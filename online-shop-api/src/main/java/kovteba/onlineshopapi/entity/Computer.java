package kovteba.onlineshopapi.entity;//package kovteba.onlineshopapi.entity;
//
//import kovteba.onlineshopapi.util.ResponceType;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.util.List;
//import java.util.Map;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "computers")
//public class Computer extends Product implements ResponceType {
//
//    @Column(name = "type", nullable = false)
//    private String type;
//
//    public Computer() {
//    }
//
//    public Computer(String brand, String model, Long price, String ean, List<String> listPic, Map<String, String> productInfo, String type) {
//        super(brand, model, price, ean, listPic, productInfo);
//        this.type = type;
//    }
//
//    @Override
//    public String toString() {
//        return "Computer{" + super.toString() +
//                "type='" + type + '\'' +
//                '}';
//    }
//}
//
//
