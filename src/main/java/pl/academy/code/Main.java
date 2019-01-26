package pl.academy.code;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pl.academy.code.model.Car;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Main {
    private static SessionFactory factory;
    public static void main(String[] args) {
        factory = new Configuration().configure().buildSessionFactory();

        Car car = new Car();

        car.setBrand("BMW");
        car.setModel("5");
        car.setCapacity(3.3);
        car.setPower(300);
        car.setDate(new Date());

        Main.persistCar(car);

        System.out.println(Main.getCars());

        System.exit(0);
    }

    public static void persistCar(Car car) {
        Session session = Main.factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(car);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public static List<Car> getCars() {
        Session session = Main.factory.openSession();

        List<Car> result = new ArrayList<Car>();
        List cars = session.createQuery("FROM pl.academy.code.model.Car").list();

        for(Iterator iterator = cars.iterator(); iterator.hasNext();) {
            Car c = (Car) iterator.next();
            result.add(c);
        }
        return result;
    }
}
