import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {
	BigInteger p;
	BigInteger q;
	BigInteger n;
	BigInteger euler;
	BigInteger e;
	BigInteger d;
	public RSA (int bits) {
		SecureRandom r = new SecureRandom();
		p = new BigInteger(bits/2, 100, r);
		q = new BigInteger(bits/2, 100, r);
		n = p.multiply(q);
		euler = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		e = new BigInteger("3");
		BigInteger dwa = new BigInteger("2");
	    while (euler.gcd(e).intValue() > 1) {
	      e = e.add(dwa);
	    }
	    d = e.modInverse(euler);
	}
	
	public synchronized String encrypt(String message) {
	    return (new BigInteger(message.getBytes())).modPow(e, n).toString();
	}
	public synchronized BigInteger encrypt(BigInteger message) {
	    return message.modPow(e, n);
	}
	public synchronized String decrypt(String message) {
	    return new String((new BigInteger(message)).modPow(d, n).toByteArray());
	}
	public synchronized BigInteger decrypt(BigInteger message) {
		return message.modPow(d, n);
	}
	
	public static void main(String[] args) {
	    RSA rsa = new RSA(4096);
	    
	    long t0, t1, t2;// , t3;
		t0 = System.currentTimeMillis();
		String ciphertext = rsa.encrypt("Ala ma kota, a sierotka ma rysia");
		t1 = System.currentTimeMillis();
		System.out.println("enc time: " + (t1 - t0));
		String plaintext2 = rsa.decrypt(ciphertext);
		t2 = System.currentTimeMillis();
		System.out.println("crt time: " + (t2 - t1));
		System.out.println(plaintext2);
	    
		/*
	    String text1 = "Yellow and Black Border Collies";
	    System.out.println("Plaintext: " + text1);
	    BigInteger plaintext = new BigInteger(text1.getBytes());

	    BigInteger ciphertext = rsa.encrypt(plaintext);
	    System.out.println("Ciphertext: " + ciphertext);
	    plaintext = rsa.decrypt(ciphertext);

	    String text2 = new String(plaintext.toByteArray());
	    System.out.println("Plaintext: " + text2);
	    */
	}
}
