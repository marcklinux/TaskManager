-- Insert Status
INSERT INTO status (id, name) VALUES
(1, 'Pendiente'),
(2, 'En Progreso'),
(3, 'Completado'),
(4, 'Cancelado');

-- Insert Periods
INSERT INTO periods (id, name, description) VALUES
(1, 'Q2 2026', 'Abril a Junio 2026'),
(2, 'Q3 2026', 'Julio a Septiembre 2026'),
(3, 'Q4 2026', 'Octubre a Diciembre 2026');

-- Insert Proyects
INSERT INTO proyects (id, name, description, status_id, period_id, start_date, end_date) VALUES
(1, 'Sistema de Gestion de Tareas', 'Desarrollo de una aplicacion web para gestionar proyectos, planes y tareas', 2, 1, '2026-06-15', '2026-07-05'),
(2, 'Portal de Cliente', 'Crear un portal para que los clientes consulten el estado de sus pedidos', 2, 2, '2026-07-06', '2026-07-19'),
(3, 'API REST Backend', 'Desarrollo de API REST para integracion con servicios externos', 1, 2, '2026-07-20', '2026-08-02');

-- Insert Plans (cada plan dura 1 semana)
INSERT INTO plans (id, proyect_id, status_id, title, description, start_date, end_date) VALUES
(1, 1, 2, 'Fase 1: Diseno y Analisis', 'Diseno del sistema y analisis de requisitos', '2026-06-15', '2026-06-21'),
(2, 1, 2, 'Fase 2: Desarrollo Backend', 'Implementacion de servicios backend y base de datos', '2026-06-22', '2026-06-28'),
(3, 1, 1, 'Fase 3: Desarrollo Frontend', 'Implementacion de la interfaz de usuario', '2026-06-29', '2026-07-05'),
(4, 2, 2, 'Diseno UX/UI', 'Diseno de la interfaz de usuario del portal', '2026-07-06', '2026-07-12'),
(5, 2, 1, 'Implementacion', 'Codificacion del portal', '2026-07-13', '2026-07-19'),
(6, 3, 2, 'Diseno de Endpoints', 'Definir y documentar endpoints API', '2026-07-20', '2026-07-26'),
(7, 3, 1, 'Implementacion API', 'Codificacion de los endpoints', '2026-07-27', '2026-08-02');

-- Insert Tasks (fechas dentro de la semana de su plan)
INSERT INTO tasks (id, title, description, status_id, plan_id, task_date) VALUES
(1, 'Recopilar Requisitos', 'Reunion con stakeholders para recopilar requisitos del sistema', 3, 1, '2026-06-15'),
(2, 'Crear Diagrama ER', 'Disenar el modelo de entidad-relacion de la base de datos', 3, 1, '2026-06-17'),
(3, 'Documentar Especificaciones', 'Crear documento con especificaciones funcionales', 2, 1, '2026-06-20'),
(4, 'Configurar Entorno de Desarrollo', 'Configurar Spring Boot y base de datos', 3, 2, '2026-06-22'),
(5, 'Crear Entidades JPA', 'Mapear entidades de base de datos con JPA', 3, 2, '2026-06-23'),
(6, 'Implementar Repositorios', 'Crear repositorios para acceso a datos', 2, 2, '2026-06-24'),
(7, 'Implementar Servicios', 'Desarrollar logica de negocio en servicios', 2, 2, '2026-06-26'),
(8, 'Crear Controladores REST', 'Implementar endpoints REST', 1, 2, '2026-06-28'),
(9, 'Configurar Proyecto Angular', 'Inicializar proyecto con Angular CLI', 1, 3, '2026-06-29'),
(10, 'Disenar Componentes', 'Crear componentes principales de la aplicacion', 1, 3, '2026-07-01'),
(11, 'Implementar Servicios Angular', 'Crear servicios para llamadas HTTP', 1, 3, '2026-07-04'),
(12, 'Disenar Wireframes', 'Crear wireframes del portal de cliente', 3, 4, '2026-07-06'),
(13, 'Crear Mockups', 'Disenar mockups de alta fidelidad', 2, 4, '2026-07-08'),
(14, 'Validar Diseno', 'Revisar diseno con equipo', 2, 4, '2026-07-11'),
(15, 'Configurar Frontend', 'Configurar React o Vue.js', 1, 5, '2026-07-13'),
(16, 'Integracion con API', 'Conectar frontend con backend', 1, 5, '2026-07-15'),
(17, 'Testing y Depuracion', 'Realizar pruebas de la aplicacion', 1, 5, '2026-07-18'),
(18, 'Definir Endpoints CRUD', 'Documentar endpoints para CRUD de recursos', 3, 6, '2026-07-20'),
(19, 'Especificar Autenticacion', 'Definir mecanismo de autenticacion', 2, 6, '2026-07-22'),
(20, 'Crear Documentacion Swagger', 'Documentar API con Swagger/OpenAPI', 2, 6, '2026-07-25'),
(21, 'Implementar Autenticacion JWT', 'Agregar seguridad JWT a la API', 1, 7, '2026-07-27'),
(22, 'Implementar Validaciones', 'Agregar validaciones de entrada', 1, 7, '2026-07-29'),
(23, 'Implementar Manejo de Errores', 'Crear middleware de manejo global de errores', 1, 7, '2026-07-31'),
(24, 'Testing Unitario', 'Escribir tests unitarios para servicios', 1, 7, '2026-08-02');
