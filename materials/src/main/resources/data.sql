-- Insertar departamentos
INSERT INTO departments (code, name) VALUES
('DC', 'Distrito Capital'),
('ANT', 'Antioquia'),
('VAL', 'Valle del Cauca'),
('ATL', 'Atlántico'),
('SAN', 'Santander'),
('CUN', 'Cundinamarca'),
('BOL', 'Bolívar'),
('NAR', 'Nariño'),
('TOL', 'Tolima'),
('HUI', 'Huila');

-- Insertar ciudades
INSERT INTO cities (code, name, department_code) VALUES
-- Distrito Capital
('BOG', 'Bogotá', 'DC'),

-- Antioquia
('MED', 'Medellín', 'ANT'),
('ENV', 'Envigado', 'ANT'),
('ITA', 'Itagüí', 'ANT'),
('BEL', 'Bello', 'ANT'),

-- Valle del Cauca
('CAL', 'Cali', 'VAL'),
('PAL', 'Palmira', 'VAL'),
('TUL', 'Tuluá', 'VAL'),

-- Atlántico
('BAQ', 'Barranquilla', 'ATL'),
('SOL', 'Soledad', 'ATL'),
('MAL', 'Malambo', 'ATL'),

-- Santander
('BUC', 'Bucaramanga', 'SAN'),
('FLO', 'Floridablanca', 'SAN'),
('GIR', 'Girón', 'SAN'),

-- Cundinamarca
('CHI', 'Chía', 'CUN'),
('ZIP', 'Zipaquirá', 'CUN'),
('FUS', 'Fusagasugá', 'CUN'),

-- Bolívar
('CAR', 'Cartagena', 'BOL'),
('MAG', 'Magangué', 'BOL'),

-- Nariño
('PAS', 'Pasto', 'NAR'),
('TUM', 'Tumaco', 'NAR'),

-- Tolima
('IBA', 'Ibagué', 'TOL'),
('ESP', 'Espinal', 'TOL'),

-- Huila
('NEI', 'Neiva', 'HUI'),
('PIH', 'Pitalito', 'HUI');

-- Insertar materiales de ejemplo
INSERT INTO materials (name, description, type, price, purchase_date, sale_date, status, city_code, created_at, updated_at) VALUES
('Laptop Dell Inspiron 15', 'Laptop para desarrollo con 16GB RAM y 512GB SSD', 'ELECTRONICO', 2500000.00, '2024-01-15', NULL, 'ACTIVE', 'BOG', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Taladro Bosch Professional', 'Taladro percutor inalámbrico 18V con batería de litio', 'HERRAMIENTA', 450000.00, '2024-01-20', NULL, 'AVAILABLE', 'MED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Ácido Sulfúrico H2SO4', 'Ácido sulfúrico concentrado al 98% para procesos industriales', 'QUIMICO', 180000.00, '2024-02-01', NULL, 'ASSIGNED', 'CAL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tela Algodón Orgánico', 'Rollo de tela de algodón orgánico 100% natural, 50 metros', 'TEXTIL', 320000.00, '2024-02-10', NULL, 'ACTIVE', 'BAQ', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cemento Portland Tipo I', 'Bulto de cemento Portland de 50kg para construcción', 'CONSTRUCCION', 25000.00, '2024-02-15', '2024-03-01', 'ACTIVE', 'BUC', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Monitor Samsung 27"', 'Monitor LED Full HD 27 pulgadas para oficina', 'ELECTRONICO', 850000.00, '2024-02-20', NULL, 'AVAILABLE', 'BOG', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Destornillador Set Profesional', 'Set de 32 destornilladores profesionales magnéticos', 'HERRAMIENTA', 120000.00, '2024-03-01', NULL, 'ACTIVE', 'MED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Resina Epóxica Transparente', 'Resina epóxica de alta calidad para recubrimientos', 'QUIMICO', 95000.00, '2024-03-05', NULL, 'ASSIGNED', 'CAL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Impresora Multifuncional Canon', 'Impresora láser multifuncional con escáner y fax', 'ELECTRONICO', 1200000.00, '2024-03-10', NULL, 'ACTIVE', 'CAR', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Silla Ergonómica de Oficina', 'Silla ejecutiva ergonómica con soporte lumbar ajustable', 'OFICINA', 650000.00, '2024-03-15', NULL, 'AVAILABLE', 'PAS', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Martillo Carpintero 16oz', 'Martillo de carpintero con mango de fibra de vidrio', 'HERRAMIENTA', 85000.00, '2024-03-20', NULL, 'ACTIVE', 'IBA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fibra de Vidrio Rollo', 'Rollo de fibra de vidrio para refuerzos estructurales', 'CONSTRUCCION', 280000.00, '2024-03-25', NULL, 'ASSIGNED', 'NEI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Escritorio Ejecutivo Madera', 'Escritorio ejecutivo de madera maciza con cajones', 'OFICINA', 890000.00, '2024-04-01', NULL, 'ACTIVE', 'CHI', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cable UTP Cat6 Rollo', 'Rollo de cable UTP categoría 6 de 305 metros', 'ELECTRONICO', 380000.00, '2024-04-05', NULL, 'AVAILABLE', 'BOG', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lana Merino Premium', 'Lana merino de alta calidad para textiles de lujo', 'TEXTIL', 520000.00, '2024-04-10', NULL, 'ACTIVE', 'MED', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);