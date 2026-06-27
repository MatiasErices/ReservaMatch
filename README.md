# 🏟️ Sistema de Reservas: ReservaMatch

## 📝 Descripción del Proyecto
ReservaMatch es un sistema basado en una arquitectura de microservicios desarrollado con Spring Boot. Su objetivo principal es gestionar las reservas de recintos deportivos, administrando usuarios, sedes, canchas, horarios, implementos y pagos de manera escalable y eficiente.

## 👥 Equipo de Desarrollo
* Matias Erices
* Lucciano Alarcon
* Vicente Fernandez

## 🛠️ Tecnologías y Herramientas Utilizadas
* **Framework:** Spring Boot (Java)
* **Arquitectura:** Microservicios
* **Comunicación:** Spring Cloud OpenFeign
* **Enrutamiento y Descubrimiento:** API Gateway y Eureka Server
* **Base de Datos:** MySQL / Spring Data JPA
* **Pruebas Unitarias:** JUnit 5 y Mockito (Patrón AAA)
* **Control de Versiones:** Git y GitHub
* **Gestión de Tareas:** Trello

## 📦 Estructura de Microservicios
El ecosistema está compuesto por los siguientes servicios:
1. `Eureka Server` (Registro de servicios)
2. `API Gateway` (Punto de entrada único)
3. `UsuarioMS`
4. `SedeMS`
5. `CanchaMS`
6. `HorarioMS`
7. `ImplementoMS`
8. `ReservaMS`
9. `PagoMS`
10. `ArriendoImplementoMS`

## 🚀 Instrucciones de Ejecución
Para levantar este proyecto de manera local, se debe seguir el siguiente orden estricto de ejecución para evitar errores de conexión:
1. Iniciar **Eureka Server**.
2. Iniciar **API Gateway**.
3. Iniciar el resto de los microservicios proveedores (Usuario, Sede, Implemento, Horario).
4. Iniciar los microservicios consumidores (Cancha, Reserva, Pago, Arriendo).

## 🔗 Enlaces Importantes
* **Tablero de Trello:** https://trello.com/invite/b/6a3ef1e82d00d91507151475/ATTI479fc65fd1109f80affbdb6f2f3a603d51C1C45F/reservamatch-gestion-de-proyecto
