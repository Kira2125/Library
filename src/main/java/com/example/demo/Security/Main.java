package com.example.demo.Security;

import lombok.Getter;
import lombok.Setter;

public class Main {
    public static void main(String[] args) {
        Construct construct = new Construct(new SimpleSite());
        System.out.println(construct.doWork());
    }
}

abstract class Builder {
    Site site;

    void createSite() {
        site = new Site();
    }

    abstract void buildName();

    abstract void buildType();

    abstract void buildPrice();

    Site getSite() {
        return site;
    }

}

class EnterpriseSite extends Builder{

    @Override
    void buildName() {
        site.setName("Facebook");
    }

    @Override
    void buildType() {
        site.setType("Enterprice");
    }

    @Override
    void buildPrice() {
        site.setPrice(400);
    }
}

class SimpleSite extends Builder{
    @Override
    void buildName() {
        site.setName("Lol");
    }

    @Override
    void buildType() {
        site.setType("Simple");
    }

    @Override
    void buildPrice() {
        site.setPrice(4);
    }
}

class Construct {
    Builder builder;

    public Construct(Builder builder) {
        this.builder = builder;
    }

    public Site doWork() {
        builder.createSite();
        builder.buildName();
        builder.buildType();
        builder.buildPrice();
        return builder.getSite();
    }
}

@Getter
@Setter
class Site {
    private String type;
    private String name;
    private int price;
    public Site() {

    }

    @Override
    public String toString() {
        return "Site{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
