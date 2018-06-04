package ie.myinfinity.demo.creditcard.security.auth.jwt.extractor;


public interface TokenExtractor {
    String extract(String payload);
}
