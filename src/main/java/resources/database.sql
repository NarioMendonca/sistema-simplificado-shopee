-- SQL adaptado para utilizar postgres

-- Tabela de usuários
-- enum não pode ser criado junto com o campo
CREATE TYPE tipo_usuario AS ENUM('cliente', 'vendedor');

CREATE TABLE usuario (
  id SERIAL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(255) NOT NULL,
  tipo tipo_usuario NOT NULL,
  data_cadastro TIMESTAMPTZ DEFAULT NOW(),
  ativo BOOLEAN DEFAULT TRUE
);

-- tabela de clientes (dados específicos)
CREATE TABLE cliente (
  id SERIAL PRIMARY KEY,
  usuario_id INTEGER UNIQUE,
  -- decidi utilizar CHAR(14) pois CNPJ tem um tamanho fixo e estou salvando sem formatação
  cpf CHAR(11),
  telefone VARCHAR(20),
  data_nascimento DATE,
  endereco TEXT,
  
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- tabela de vendedores (dados específicos)
CREATE TABLE vendedor (
  id SERIAL PRIMARY KEY,
  usuario_id INTEGER UNIQUE,
  -- decidi utilizar CHAR(14) pois CNPJ tem um tamanho fixo e estou salvando sem formatação
  cnpj CHAR(14) UNIQUE,
  razao_social VARCHAR(200),
  telefone VARCHAR(20),
  avaliacao DECIMAL(3,2) DEFAULT 5.0,
  
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Tabela de produtos
CREATE TABLE produto (
  id SERIAL PRIMARY KEY,
  vendedor_id INTEGER NOT NULL,
  nome VARCHAR(200) NOT NULL,
  descricao TEXT,
  categoria VARCHAR(100),
  preco DECIMAL(10,2) NOT NULL,
  quantidade_estoque INTEGER DEFAULT 0,
  imagem_url VARCHAR(500),
  data_cadastro TIMESTAMPTZ DEFAULT now(),
  ativo BOOLEAN DEFAULT TRUE,
  
  FOREIGN KEY (vendedor_id) REFERENCES vendedor(id)
);

-- Tabela de pedidos
CREATE TYPE pedido_status AS ENUM('aguardando', 'pago', 'enviado', 'entregue', 'cancelado');

CREATE TABLE pedido (
  id SERIAL PRIMARY KEY,
  cliente_id INTEGER NOT NULL,
  data_pedido TIMESTAMPTZ DEFAULT now(),
  status pedido_status DEFAULT 'aguardando',
  valor_total DECIMAL(10,2) NOT NULL,
  metodo_pagamento VARCHAR(50),
  endereco_entrega TEXT,
  
  FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabela de itens do pedido
CREATE TABLE item_pedido (
  id SERIAL PRIMARY KEY,
  pedido_id INTEGER NOT NULL,
  produto_id INTEGER NOT NULL,
  quantidade INTEGER NOT NULL,
  preco_unitario DECIMAL(10,2) NOT NULL,
  
  FOREIGN KEY (pedido_id) REFERENCES pedido(id),
  FOREIGN KEY (produto_id) REFERENCES produto(id)
);

CREATE TYPE nivel_log AS ENUM('INFO', 'WARNING', 'ERROR');

-- Tabela de logs
CREATE TABLE log_sistema (
  id SERIAL PRIMARY KEY,
  data_hora TIMESTAMPTZ DEFAULT NOW(),
  nivel nivel_log NOT NULL,
  mensagem TEXT NOT NULL,
  usuario_id INTEGER,
  
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- scripts de usuarios padrão

INSERT INTO usuario (nome, email, senha, tipo) VALUES ('Cliente padrao', 'cliente@gmail.com', 'cliente123', 'cliente');
INSERT INTO cliente (usuario_id, cpf, telefone, data_nascimento, endereco) VALUES (1, '12345678901', '88996669999', '2000-12-12', 'Rua dos arroios, 13, 63660000');
INSERT INTO usuario (nome, email, senha, tipo) VALUES ('Vendedor padrao', 'vendedor@gmail.com', 'vendedor123', 'vendedor');
INSERT INTO vendedor (usuario_id, cnpj, razao_social, telefone) VALUES (2, '12345678901234', 'Loja de eletronicos', '88997109999');
