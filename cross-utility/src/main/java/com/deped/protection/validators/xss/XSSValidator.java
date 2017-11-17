package com.deped.protection.validators.xss;

import org.owasp.validator.html.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URL;

public class XSSValidator implements ConstraintValidator<XSS, String> {

    @Override
    public void initialize(XSS constraintAnnotation) {

    }

    //TODO to be continued
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            URL url = this.getClass().getClassLoader().getResource("antisamy-ebay.xml");
            Policy policy = Policy.getInstance(url);

            AntiSamy antiSamy = new AntiSamy();
            value = value.trim();
            CleanResults cleanResults = antiSamy.scan(value, policy);
            String cleaned = cleanResults.getCleanHTML().trim();
            if (!value.equals(cleaned)) {
                return false;
            }
        } catch (PolicyException e) {
            e.printStackTrace();
            return false;
        } catch (ScanException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String x = "<ScrIpt> #21 Maleki St- Niroo Havayi St- ";
        boolean s = new XSSValidator().isValid(x, null);
        System.out.println(s);
    }
}
