# Proyecto-Sockets

# comandos usados 
javac -cp "lib/*" -d bin src/db/*.java src/modelo/*.java src/service/*.java src/main.java

java -cp "bin;lib/*;resources" main 

# comandos de postgre

-- ==========================================
-- 1. ELIMINAR TABLAS EXISTENTES (Para empezar de cero)
-- ==========================================
-- (Ojo: El orden importa por las llaves foráneas)
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS comentario CASCADE;
DROP TABLE IF EXISTS mensaje CASCADE;
DROP TABLE IF EXISTS publicacion CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS tipo_publicacion CASCADE;
DROP TABLE IF EXISTS tipo_usuario CASCADE;

-- ==========================================
-- 2. CREAR TABLAS CATÁLOGO (Sin llaves foráneas)
-- ==========================================
CREATE TABLE tipo_usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE tipo_publicacion (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

-- ==========================================
-- 3. CREAR TABLA USUARIO (Depende de tipo_usuario)
-- ==========================================
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    tipo_usuario_id INT REFERENCES tipo_usuario(id),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    foto_perfil VARCHAR(255),
    biografia TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 4. CREAR TABLA PUBLICACIÓN (Depende de usuario y tipo_publicacion)
-- ==========================================
CREATE TABLE publicacion (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    tipo_publicacion_id INT REFERENCES tipo_publicacion(id),
    url_contenido VARCHAR(255),
    caption TEXT,
    ubicacion VARCHAR(100),
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 5. CREAR TABLA COMENTARIO (Depende de usuario y publicacion)
-- ==========================================
CREATE TABLE comentario (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    publicacion_id INT REFERENCES publicacion(id) ON DELETE CASCADE,
    texto TEXT NOT NULL,
    fecha_comentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ==========================================
-- 6. CREAR TABLA LIKE (Depende de usuario y publicacion)
-- ==========================================
CREATE TABLE likes (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    publicacion_id INT REFERENCES publicacion(id) ON DELETE CASCADE,
    fecha_like TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(usuario_id, publicacion_id) -- Evita que un usuario dé like 2 veces a la misma foto
);

-- ==========================================
-- 7. CREAR TABLA MENSAJE (Depende de usuario dos veces)
-- ==========================================
CREATE TABLE mensaje (
    id SERIAL PRIMARY KEY,
    emisor_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    receptor_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    contenido TEXT NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ENVIADO'
);

-- ==========================================
-- 8. INSERTAR DATOS DE PRUEBA (Para los catálogos)
-- ==========================================
-- Esto es necesario para que cuando insertes un usuario, el tipo_usuario_id (1, 2, 3) exista.

INSERT INTO tipo_usuario (nombre) VALUES 
('Usuario_Personal'), 
('Usuario_Influencer'), 
('Usuario_Empresarial');

INSERT INTO tipo_publicacion (nombre) VALUES 
('Publicaciones_Individuales'), 
('Reels'), 
('Historias'), 
('Publicaciones_colaborativas');


# comandos de SQLServer

USE Instagram;
GO

DROP TABLE IF EXISTS mensaje;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS publicacion;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS tipo_publicacion;
DROP TABLE IF EXISTS tipo_usuario;
GO

CREATE TABLE tipo_usuario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE tipo_publicacion (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE usuario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    tipo_usuario_id INT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    foto_perfil VARCHAR(255),
    biografia VARCHAR(MAX), 
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id)
);

CREATE TABLE publicacion (
    id INT IDENTITY(1,1) PRIMARY KEY,
    usuario_id INT,
    tipo_publicacion_id INT,
    url_contenido VARCHAR(255),
    caption VARCHAR(MAX),
    ubicacion VARCHAR(100),
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id), 
    FOREIGN KEY (tipo_publicacion_id) REFERENCES tipo_publicacion(id)
);

CREATE TABLE comentario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    usuario_id INT,
    publicacion_id INT,
    texto VARCHAR(MAX) NOT NULL,
    fecha_comentario DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id), 
    FOREIGN KEY (publicacion_id) REFERENCES publicacion(id) 
);

CREATE TABLE likes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    usuario_id INT,
    publicacion_id INT,
    fecha_like DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (publicacion_id) REFERENCES publicacion(id),
    CONSTRAINT unique_like UNIQUE(usuario_id, publicacion_id)
);

CREATE TABLE mensaje (
    id INT IDENTITY(1,1) PRIMARY KEY,
    emisor_id INT,
    receptor_id INT,
    contenido VARCHAR(MAX) NOT NULL,
    fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'ENVIADO',
    FOREIGN KEY (emisor_id) REFERENCES usuario(id),
    FOREIGN KEY (receptor_id) REFERENCES usuario(id)
);

INSERT INTO tipo_usuario (nombre) VALUES ('Usuario_Personal'), ('Usuario_Influencer'), ('Usuario_Empresarial');
INSERT INTO tipo_publicacion (nombre) VALUES ('Publicaciones_Individuales'), ('Reels'), ('Historias'), ('Publicaciones_colaborativas');
GO