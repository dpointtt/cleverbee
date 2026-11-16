package com.dpointtt.cleverbee;

import com.dpointtt.cleverbee.model.*;
import com.dpointtt.cleverbee.model.Hive.HiveStatus;
import com.dpointtt.cleverbee.model.Hive.HiveType;
import com.dpointtt.cleverbee.model.Sensor.SensorType;
import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final EntityManager em;

    public DataSeeder(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void run(String... args) {

        if (em.createQuery("select count(a) from Apiary a", Long.class).getSingleResult() > 0) {
            return;
        }

        Apiary a1 = apiary("AP-001", "Карпатська", "Івано-Франківська обл.", 48.92, 24.71,
                "Гірська пасіка, акацієвий та лісовий мед");
        Apiary a2 = apiary("AP-002", "Степова", "Херсонська обл.", 46.63, 32.61,
                "Пасіка біля соняшникових полів");

        Hive h1 = hive("HV-1001", HiveType.DADANT, HiveStatus.ACTIVE, 2025, "Сильна родина");
        Hive h2 = hive("HV-1002", HiveType.LANGSTROTH, HiveStatus.WINTERING, 2024, "Потребує підгодівлі");
        Hive h3 = hive("HV-2001", HiveType.WARRE, HiveStatus.ACTIVE, 2026, null);
        a1.addHive(h1);
        a1.addHive(h2);
        a2.addHive(h3);
        em.persist(a1);
        em.persist(a2);

        Sensor weight = sensor("SN-W-001", SensorType.WEIGHT, "1.2.0");
        Sensor temp = sensor("SN-T-001", SensorType.TEMPERATURE, "1.0.4");
        Sensor hum = sensor("SN-H-001", SensorType.HUMIDITY, "1.0.4");
        em.persist(weight);
        em.persist(temp);
        em.persist(hum);

        h1.getSensors().add(weight);
        h1.getSensors().add(temp);
        h2.getSensors().add(temp);
        h2.getSensors().add(hum);
        h3.getSensors().add(weight);
        weight.getHives().add(h1);
        weight.getHives().add(h3);
        temp.getHives().add(h1);
        temp.getHives().add(h2);
        hum.getHives().add(h2);

        Beekeeper b1 = beekeeper("Ivan Melnik", "ivan@cleverbee.com", "+380501112233");
        Beekeeper b2 = beekeeper("Julia Koval", "olena@cleverbee.com", "+380671234567");
        b1.getApiaries().add(a1);
        b1.getApiaries().add(a2);
        b2.getApiaries().add(a1);
        a1.getBeekeepers().add(b1);
        a1.getBeekeepers().add(b2);
        a2.getBeekeepers().add(b1);
        em.persist(b1);
        em.persist(b2);

        reading(h1, weight, 42.7, "kg");
        reading(h1, temp, 34.8, "°C");
        reading(h2, temp, 18.2, "°C");
        reading(h2, hum, 61.5, "%");
        reading(h3, weight, 38.1, "kg");

        inspection(h1, b1, 8, true, "Багато розплоду, місця достатньо");
        inspection(h2, b2, 5, false, "Матку не знайдено, слабка родина");
        inspection(h3, b1, 7, true, null);
    }

    private Apiary apiary(String code, String name, String loc, double lat, double lon, String desc) {
        Apiary a = new Apiary();
        a.setCode(code);
        a.setName(name);
        a.setLocation(loc);
        a.setLatitude(lat);
        a.setLongitude(lon);
        a.setDescription(desc);
        return a;
    }

    private Hive hive(String sn, HiveType type, HiveStatus status, Integer queenYear, String notes) {
        Hive h = new Hive();
        h.setSerialNumber(sn);
        h.setType(type);
        h.setStatus(status);
        h.setQueenYear(queenYear);
        h.setNotes(notes);
        return h;
    }

    private Sensor sensor(String code, SensorType type, String fw) {
        Sensor s = new Sensor();
        s.setDeviceCode(code);
        s.setType(type);
        s.setFirmwareVersion(fw);
        s.setActive(true);
        return s;
    }

    private Beekeeper beekeeper(String name, String email, String phone) {
        Beekeeper b = new Beekeeper();
        b.setFullName(name);
        b.setEmail(email);
        b.setPhone(phone);
        return b;
    }

    private void reading(Hive h, Sensor s, double value, String unit) {
        SensorReading r = new SensorReading();
        r.setHive(h);
        r.setSensor(s);
        r.setValue(value);
        r.setUnit(unit);
        r.setRecordedAt(LocalDateTime.now());
        em.persist(r);
    }

    private void inspection(Hive h, Beekeeper b, int strength, boolean queenSeen, String notes) {
        Inspection i = new Inspection();
        i.setHive(h);
        i.setBeekeeper(b);
        i.setInspectedAt(LocalDateTime.now());
        i.setColonyStrength(strength);
        i.setQueenSeen(queenSeen);
        i.setNotes(notes);
        em.persist(i);
    }
}
