package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CourseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Course getCourseSample1() {
        return new Course()
            .id(1L)
            .externalId(1L)
            .versionNumber(1)
            .module("module1")
            .title("title1")
            .creator("creator1")
            .usesCount(1)
            .route("route1")
            .imageUrl("imageUrl1")
            .avatarUrl("avatarUrl1");
    }

    public static Course getCourseSample2() {
        return new Course()
            .id(2L)
            .externalId(2L)
            .versionNumber(2)
            .module("module2")
            .title("title2")
            .creator("creator2")
            .usesCount(2)
            .route("route2")
            .imageUrl("imageUrl2")
            .avatarUrl("avatarUrl2");
    }

    public static Course getCourseRandomSampleGenerator() {
        return new Course()
            .id(longCount.incrementAndGet())
            .externalId(longCount.incrementAndGet())
            .versionNumber(intCount.incrementAndGet())
            .module(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .creator(UUID.randomUUID().toString())
            .usesCount(intCount.incrementAndGet())
            .route(UUID.randomUUID().toString())
            .imageUrl(UUID.randomUUID().toString())
            .avatarUrl(UUID.randomUUID().toString());
    }
}
