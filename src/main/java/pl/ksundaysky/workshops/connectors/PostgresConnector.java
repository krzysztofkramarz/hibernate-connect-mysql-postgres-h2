package pl.ksundaysky.workshops.connectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import pl.ksundaysky.workshops.model.Person;
import pl.ksundaysky.workshops.model.Umowa;

import java.util.Properties;

/**
 * @author krzysztof.niedzielski
 */
public class PostgresConnector implements Connector {

    private static SessionFactory sessionFactory;

    public  SessionFactory getSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "org.postgresql.Driver");
            settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
            settings.put(Environment.USER, "postgres");
            settings.put(Environment.PASS, "postgres");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Umowa.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessionFactory;
    }

    @Override
    public Session getSession() {
        return this.getSessionFactory().getCurrentSession();
    }
}