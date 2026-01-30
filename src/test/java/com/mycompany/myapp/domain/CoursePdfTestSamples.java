package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CoursePdfTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CoursePdf getCoursePdfSample1() {
        return new CoursePdf().id(1L).lectureUrl("lectureUrl1").tasksUrl("tasksUrl1");
    }

    public static CoursePdf getCoursePdfSample2() {
        return new CoursePdf().id(2L).lectureUrl("lectureUrl2").tasksUrl("tasksUrl2");
    }

    public static CoursePdf getCoursePdfRandomSampleGenerator() {
        return new CoursePdf()
            .id(longCount.incrementAndGet())
            .lectureUrl(UUID.randomUUID().toString())
            .tasksUrl(UUID.randomUUID().toString());
    }
}
