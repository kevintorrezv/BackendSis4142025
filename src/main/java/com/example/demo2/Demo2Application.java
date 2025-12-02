package com.example.demo2;

// --- IMPORTACIONES NECESARIAS ---
import com.example.demo2.models.Escoba;
import com.example.demo2.models.Fabricante;
import com.example.demo2.repository.EscobaRepository;
import com.example.demo2.repository.FabricanteRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// --- CONFIGURACIÓN DE SWAGGER / OPENAPI PARA NGROK ---
@OpenAPIDefinition(
        info = @Info(
                title = "API de Fabricantes y Escobas",
                version = "1.0.0",
                description = "API para gestionar el catálogo de fabricantes y sus productos."
        ),
        servers = {
                // ¡MUY IMPORTANTE! Reemplaza esta URL con la que te genera Ngrok cada vez que lo inicies.
                @Server(url = "https://semihumanized-stoopingly-emerald.ngrok-free.dev", description = "Servidor de Producción (Ngrok)"),
                // Dejamos el servidor local para pruebas en tu máquina.
                @Server(url = "http://localhost:8080", description = "Servidor de Desarrollo Local")
        }
)
// --------------------------------------------------------
@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    // --- CÓDIGO PARA SIMULAR DATOS AL INICIAR LA APLICACIÓN ---
    @Bean
    CommandLineRunner commandLineRunner(FabricanteRepository fabricanteRepository, EscobaRepository escobaRepository) {
        return args -> {
            // --- 1. Crear y guardar Fabricantes reales ---
            Fabricante fabricanteA = new Fabricante();
            fabricanteA.setNombre("Cepillos Industriales S.A.");
            fabricanteA.setAnoFundacion(1985);
            fabricanteA.setSitioWeb("https://www.cepillossa.com");
            fabricanteRepository.save(fabricanteA);

            Fabricante fabricanteB = new Fabricante();
            fabricanteB.setNombre("Maderas Limpias de Obregón");
            fabricanteB.setAnoFundacion(1992);
            fabricanteB.setSitioWeb("https://www.maderaslimpias.com.mx");
            fabricanteRepository.save(fabricanteB);

            // --- 2. Crear Escobas y asignarles su Fabricante ---
            Escoba escoba1 = new Escoba();
            escoba1.setModelo("Barredora Clásica 120");
            escoba1.setColor("Natural");
            escoba1.setTipoDeMadera("Pino");
            escoba1.setLongitudCm(120);
            escoba1.setUsoRecomendado("Doméstico");
            escoba1.setFabricante(fabricanteA);
            escobaRepository.save(escoba1);

            Escoba escoba2 = new Escoba();
            escoba2.setModelo("Resistente para Exteriores");
            escoba2.setColor("Roble Oscuro");
            escoba2.setTipoDeMadera("Roble");
            escoba2.setLongitudCm(150);
            escoba2.setUsoRecomendado("Jardinería");
            escoba2.setFabricante(fabricanteB);
            escobaRepository.save(escoba2);

            Escoba escoba3 = new Escoba();
            escoba3.setModelo("Máxima Durabilidad X-500");
            escoba3.setColor("Gris Metálico");
            escoba3.setTipoDeMadera("Haya con refuerzo de metal");
            escoba3.setLongitudCm(160);
            escoba3.setUsoRecomendado("Industrial");
            escoba3.setFabricante(fabricanteA);
            escobaRepository.save(escoba3);

            System.out.println(">>> ¡Datos de prueba de fabricantes y escobas cargados! <<<");
        };
    }
}