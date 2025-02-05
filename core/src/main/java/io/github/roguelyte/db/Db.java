package io.github.roguelyte.db;

import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import io.github.roguelyte.db.tables.Enemies;
import io.github.roguelyte.db.tables.Entities;
import io.github.roguelyte.db.tables.Items;
import io.github.roguelyte.db.tables.Stats;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Db {
    SessionFactory sessionFactory;

    public Db(String path) {
        Session session = null;
        try {
            sessionFactory = new Configuration()
                .addAnnotatedClass(Entities.class)
                .addAnnotatedClass(Items.class)
                .addAnnotatedClass(Enemies.class)
                .addAnnotatedClass(Stats.class)
               .configure(path)
               .buildSessionFactory();
            log.info("Database initialized");
        } catch(Exception exception) {
            throw new RuntimeException(String.format(
                "Cannot create DB session factory: %s", exception.getMessage()));
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public <E> E with(Function<Session, E> func) {
        Session session = null;
        try {

            session = sessionFactory.openSession();
            return func.apply(session);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
