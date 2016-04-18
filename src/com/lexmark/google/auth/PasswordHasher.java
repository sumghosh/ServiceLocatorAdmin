package com.lexmark.google.auth;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

public class PasswordHasher {

    public final Integer DEFAULT_ITERATIONS = 10000;
    public final String algorithm = "pbkdf2_sha256";
    private static final Random RANDOM = new SecureRandom();

    public PasswordHasher() {
    }

    public String getEncodedHash(String password, String salt, int iterations) {
        // Returns only the last part of whole encoded password
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        try {
            gen.init(password.getBytes("UTF-8"), salt.getBytes(), iterations);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PasswordHasher.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] dk = ((KeyParameter) gen.generateDerivedParameters(256)).getKey();

        byte[] hashBase64 = Base64.encodeBase64(dk);
        return new String(hashBase64);
    }

    public String encode(String password, String salt, int iterations) {
        // returns hashed password, along with algorithm, number of iterations and salt
        String hash = getEncodedHash(password, salt, iterations);
        return String.format("%s$%d$%s$%s", algorithm, iterations, salt, hash);
    }

    public String encode(String password, String salt) {
        return this.encode(password, salt, this.DEFAULT_ITERATIONS);
    }
    
    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a 16 bytes random salt
     */
    public static byte[] getNextSalt() {
      byte[] salt = new byte[16];
      RANDOM.nextBytes(salt);
      return salt;
    }

    public boolean checkPassword(String password, String hashedPassword) {
        // hashedPassword consist of: ALGORITHM, ITERATIONS_NUMBER, SALT and
        // HASH; parts are joined with dollar character ("$")
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 4) {
            // wrong hash format
            return false;
        }
        Integer iterations = Integer.parseInt(parts[1]);
        String salt = parts[2];
        String hash = encode(password, salt, iterations);

        return hash.equals(hashedPassword);
    }

    public static void main(String args[]){
    	String pass = "password";
    	String salt = "seasalt2";
    	int iterations = 10000;
    	PasswordHasher h = new PasswordHasher();
    	String encodedHash = h.getEncodedHash(pass, salt, iterations);
    	System.out.println("encodedHash::"+ encodedHash);
    	
    	//boolean check = h.checkPassword("mystery", "pbkdf2_sha256$10000$qx1ec0f4lu4l$3G81rAm/4ng0tCCPTrx2aWohq7ztDBfFYczGNoUtiKQ=");
    	//boolean check = h.checkPassword("seasalt2", "pbkdf2_sha256$10000$seasalt2$3G81rAm/7Uini1AkM6AXI8Pnsicpdz4JqTPSxyrHJspWLGtbqxU=");
    	//boolean check = h.checkPassword("password", "pbkdf2_sha256$10000$[B@4079d7b3$gB2v4k2EXbbr4G9unQgDAq763b3uV69q6LHeFV5mEbM=");
    	boolean check = h.checkPassword("password", "pbkdf2_sha256$10000$[B@3a1af7aa$hPKnzrSf5oJb10LQfRqGG1JeJWUeIy1D7eN7EcMUV4A=");
    	
    	//boolean check = h.checkPassword("lexmark123", "pbkdf2_sha256$12000$XPz5JAVLbw9B$XC62oqzwKQv/aSzE5m+izDMIdBBCCiS8hjvUKr6u+i0=");
    	//boolean check = h.checkPassword("restpass", "pbkdf2_sha256$12000$NDOhd5W87XSN$faeyfCHJA2WG6ZcmH6mU4BrnPtvDqiHSrQE9BF6lN5g=");
    	System.out.println("check-->"+check);
    	System.out.println(h.encode(pass, getNextSalt().toString(), iterations));
    }
}