# Java CSV -> Object
A simple Java CSV parser that maps CSV to Java Objects with support for custom type adapters.
Written out of frustration with other CSV libraries, this parser aims for simplicity so I can just speedrun my school projects.

## Usage
```java
// Create a model
class ProductionOrder extends CsvModal {
    @CsvField(column = "hoofdproject")
    private String hoofdproject;

    @CsvField(column = "productieorder")
    private String productieorder;

    @CsvField(column = "versie")
    private Integer versie;
}

// Create a parser
// optionally register custom type adapters using parser.registerTypeAdapter()
CsvParser<ProductionOrder> parser = new CsvParser<>(csvContentString, ProductionOrder.class);

// Parse the CSV
Collection<ProductionOrder> productionOrders = parser.parse();
```