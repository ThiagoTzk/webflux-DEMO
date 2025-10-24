package com.example.webflux;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebfluxDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Mono<Integer> A = Mono.just(10);

        Mono<Integer> B = Mono.delay(java.time.Duration.ofSeconds(5))
                .then(Mono.just(5));

        Mono<Integer> C = Mono.zip(A, B, (a, b) -> a + b);

        System.out.println(">>> A = 10");
        System.out.println(">>> B será definido após 5 segundos (assincronamente)");
        System.out.println(">>> C = A + B -> aguardando valor...");
        System.out.println(">>> " + java.time.LocalTime.now() + " - Iniciando...");

        C.subscribe(result -> {
            System.out.println(">>> " + java.time.LocalTime.now() + " - C calculado reativamente! Resultado: " + result);
        });

        System.out.println(">>> Programa continua executando enquanto espera B...");
        System.out.println(">>> " + java.time.LocalTime.now() + " - Fazendo outras tarefas...");

        for (int i = 1; i <= 5; i++) {
            Thread.sleep(1000);
            System.out.println(">>> " + java.time.LocalTime.now() + " - Executando tarefa " + i + "/5...");
        }

        Thread.sleep(2000);
        System.out.println(">>> " + java.time.LocalTime.now() + " - Finalizando demonstração.");
    }
}