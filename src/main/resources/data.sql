-- Insert Status
INSERT INTO status (name) VALUES
('Pendiente'),
('En Progreso'),
('Completado'),
('Cancelado');

-- Insert Periods
INSERT INTO periods (name, description) VALUES
('Q2 2026', 'Abril a Junio 2026'),
('Q3 2026', 'Julio a Septiembre 2026'),
('Q4 2026', 'Octubre a Diciembre 2026');

-- Insert Proyects (status_id y period_id se asignan por orden de insercion: 1,2,3...)
INSERT INTO proyects (name, description, status_id, period_id, start_date, end_date) VALUES
('Sistema de Gestion de Tareas', 'Desarrollo de una aplicacion web para gestionar proyectos, planes y tareas', 2, 1, '2026-06-15', '2026-07-05'),
('Portal de Cliente', 'Crear un portal para que los clientes consulten el estado de sus pedidos', 2, 2, '2026-07-06', '2026-07-19'),
('API REST Backend', 'Desarrollo de API REST para integracion con servicios externos', 1, 2, '2026-07-20', '2026-08-02');

-- Insert Plans (cada plan dura 1 semana)
INSERT INTO plans (proyect_id, status_id, title, description, start_date, end_date) VALUES
(1, 2, 'Fase 1: Diseno y Analisis', 'Diseno del sistema y analisis de requisitos', '2026-06-15', '2026-06-21'),
(1, 2, 'Fase 2: Desarrollo Backend', 'Implementacion de servicios backend y base de datos', '2026-06-22', '2026-06-28'),
(1, 1, 'Fase 3: Desarrollo Frontend', 'Implementacion de la interfaz de usuario', '2026-06-29', '2026-07-05'),
(2, 2, 'Diseno UX/UI', 'Diseno de la interfaz de usuario del portal', '2026-07-06', '2026-07-12'),
(2, 1, 'Implementacion', 'Codificacion del portal', '2026-07-13', '2026-07-19'),
(3, 2, 'Diseno de Endpoints', 'Definir y documentar endpoints API', '2026-07-20', '2026-07-26'),
(3, 1, 'Implementacion API', 'Codificacion de los endpoints', '2026-07-27', '2026-08-02');

-- Insert Tasks (fechas dentro de la semana de su plan)
INSERT INTO tasks (title, description, status_id, plan_id, task_date) VALUES
('Recopilar Requisitos', 'Reunion con stakeholders para recopilar requisitos del sistema', 3, 1, '2026-06-15'),
('Crear Diagrama ER', 'Disenar el modelo de entidad-relacion de la base de datos', 3, 1, '2026-06-17'),
('Documentar Especificaciones', 'Crear documento con especificaciones funcionales', 2, 1, '2026-06-20'),
('Configurar Entorno de Desarrollo', 'Configurar Spring Boot y base de datos', 3, 2, '2026-06-22'),
('Crear Entidades JPA', 'Mapear entidades de base de datos con JPA', 3, 2, '2026-06-23'),
('Implementar Repositorios', 'Crear repositorios para acceso a datos', 2, 2, '2026-06-24'),
('Implementar Servicios', 'Desarrollar logica de negocio en servicios', 2, 2, '2026-06-26'),
('Crear Controladores REST', 'Implementar endpoints REST', 1, 2, '2026-06-28'),
('Configurar Proyecto Angular', 'Inicializar proyecto con Angular CLI', 1, 3, '2026-06-29'),
('Disenar Componentes', 'Crear componentes principales de la aplicacion', 1, 3, '2026-07-01'),
('Implementar Servicios Angular', 'Crear servicios para llamadas HTTP', 1, 3, '2026-07-04'),
('Disenar Wireframes', 'Crear wireframes del portal de cliente', 3, 4, '2026-07-06'),
('Crear Mockups', 'Disenar mockups de alta fidelidad', 2, 4, '2026-07-08'),
('Validar Diseno', 'Revisar diseno con equipo', 2, 4, '2026-07-11'),
('Configurar Frontend', 'Configurar React o Vue.js', 1, 5, '2026-07-13'),
('Integracion con API', 'Conectar frontend con backend', 1, 5, '2026-07-15'),
('Testing y Depuracion', 'Realizar pruebas de la aplicacion', 1, 5, '2026-07-18'),
('Definir Endpoints CRUD', 'Documentar endpoints para CRUD de recursos', 3, 6, '2026-07-20'),
('Especificar Autenticacion', 'Definir mecanismo de autenticacion', 2, 6, '2026-07-22'),
('Crear Documentacion Swagger', 'Documentar API con Swagger/OpenAPI', 2, 6, '2026-07-25'),
('Implementar Autenticacion JWT', 'Agregar seguridad JWT a la API', 1, 7, '2026-07-27'),
('Implementar Validaciones', 'Agregar validaciones de entrada', 1, 7, '2026-07-29'),
('Implementar Manejo de Errores', 'Crear middleware de manejo global de errores', 1, 7, '2026-07-31'),
('Testing Unitario', 'Escribir tests unitarios para servicios', 1, 7, '2026-08-02');

-- Insert Task Work Logs (registro real de dias trabajados por tarea)
INSERT INTO task_work_logs (task_id, work_date, notes) VALUES
(1, '2026-06-15', 'Sesion de levantamiento inicial con stakeholders'),
(1, '2026-06-17', 'Refinamiento de requisitos funcionales'),
(1, '2026-06-19', 'Cierre de requerimientos y pendientes'),
(2, '2026-06-17', 'Diseno inicial del modelo de datos'),
(2, '2026-06-18', 'Ajustes en relaciones y cardinalidades'),
(3, '2026-06-20', 'Version inicial del documento funcional'),
(4, '2026-06-22', 'Configuracion de proyecto y dependencias'),
(4, '2026-06-24', 'Ajustes de entorno local y perfiles'),
(6, '2026-06-24', 'Repositorio base para tareas y planes'),
(7, '2026-06-26', 'Primera version de logica de negocio'),
(9, '2026-06-29', 'Estructura base del proyecto frontend'),
(10, '2026-07-01', 'Diseno de componentes principales'),
(12, '2026-07-06', 'Wireframes de vistas del portal'),
(13, '2026-07-08', 'Mockups de alta fidelidad'),
(15, '2026-07-13', 'Configuracion de framework frontend'),
(16, '2026-07-15', 'Integracion inicial con endpoints'),
(18, '2026-07-20', 'Definicion de contratos CRUD'),
(19, '2026-07-22', 'Reglas de autenticacion definidas'),
(21, '2026-07-27', 'Implementacion inicial de JWT'),
(24, '2026-08-02', 'Cobertura base de pruebas unitarias');
