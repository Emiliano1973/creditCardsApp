package ie.myinfinity.demo.creditcard.security.auth.jwt.verifier;

public interface TokenVerifier {
    boolean verify(String jti);
}
