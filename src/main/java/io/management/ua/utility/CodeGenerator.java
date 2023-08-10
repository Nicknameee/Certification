package io.management.ua.utility;

import java.util.Random;

public class CodeGenerator {
    public static String generateCode() {
        return String.valueOf(new Random().nextInt((int) 10E3, (int) 10E4));
    }
}
