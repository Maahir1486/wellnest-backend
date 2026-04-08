package com.wellnest.config;

import com.wellnest.model.Resource;
import com.wellnest.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private final ResourceRepository resourceRepository;

    public DataSeeder(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Override
    public void run(String... args) {
        if (resourceRepository.count() == 0) {
            log.info("Seeding initial health resources...");
            resourceRepository.save(createResource(
                "Managing Stress During Exams",
                "Mental Health", "Article",
                "A comprehensive guide to keeping anxiety at bay and performing your best.",
                "https://www.apa.org/topics/stress"
            ));
            resourceRepository.save(createResource(
                "15-Minute Home Workout",
                "Fitness", "Video",
                "Quick and effective exercises without any equipment.",
                "https://www.youtube.com/watch?v=UItWltVZZmE"
            ));
            resourceRepository.save(createResource(
                "Healthy Eating on a Student Budget",
                "Nutrition", "Guide",
                "Tips and meal prep ideas that are wallet-friendly.",
                "https://www.healthline.com/nutrition/healthy-eating-for-beginners"
            ));
            resourceRepository.save(createResource(
                "Mindfulness Meditation for Beginners",
                "Mental Health", "Video",
                "A 10-minute guided meditation session to reduce stress and improve focus.",
                "https://www.youtube.com/watch?v=inpok4MKVLM"
            ));
            resourceRepository.save(createResource(
                "Sleep Hygiene for Students",
                "Mental Health", "Article",
                "Learn how proper sleep schedules dramatically improve academic performance.",
                "https://www.sleepfoundation.org/teens-and-sleep"
            ));
            resourceRepository.save(createResource(
                "Yoga for Stress Relief",
                "Fitness", "Video",
                "Gentle yoga sequences designed to release tension and boost mood.",
                "https://www.youtube.com/watch?v=hJbRpHZr_d0"
            ));
            log.info("Seeded 6 health resources.");
        }
    }

    private Resource createResource(String title, String category, String type,
                                     String description, String url) {
        Resource r = new Resource();
        r.setTitle(title);
        r.setCategory(category);
        r.setType(type);
        r.setDescription(description);
        r.setUrl(url);
        return r;
    }
}
