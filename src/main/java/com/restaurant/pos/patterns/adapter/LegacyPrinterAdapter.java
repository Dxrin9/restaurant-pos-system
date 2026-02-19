package com.restaurant.pos.patterns.adapter;

/** Legacy printer interface that we must adapt */
interface LegacyPrinter {
    void printText(String text);
}

/** DESIGN PATTERN: Adapter - wraps the legacy printer to the modern interface */
public class LegacyPrinterAdapter {

    private final LegacyPrinter legacyPrinter = new LegacyPrinterImpl();

    public void print(String text) {
        legacyPrinter.printText(text);
    }
}

class LegacyPrinterImpl implements LegacyPrinter {
    @Override
    public void printText(String text) {
        System.out.println("[LEGACY PRINTER]: " + text);
    }
}
