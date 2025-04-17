class Circle{
    int radius;
}
class Cylinder extends Circle{
    int height;
}
class Pyramid extends Cylinder{
    int height;
    int base_length;
    int base_width;
}
public class Inheritance{
    public static void main(String[] args){
        Pyramid cy = new Pyramid();
        cy.radius = 10;
        cy.height = 20;
        System.out.println(cy.radius);
        System.out.println(cy.height);
    }
}