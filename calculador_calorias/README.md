# 🥗 Calculadora de Calorias em Clojure

## 📖 Sobre o projeto

Este projeto consiste em uma calculadora de calorias desenvolvida em **Clojure**, capaz de registrar alimentos consumidos e exercícios realizados, permitindo acompanhar o saldo calórico ao longo do tempo.

A aplicação utiliza uma arquitetura cliente-servidor, onde o front-end interage com um back-end por meio de requisições HTTP e troca de dados em formato JSON.

Além disso, são utilizadas APIs externas para obter informações reais sobre alimentos e atividades físicas, tornando os cálculos mais precisos e próximos de aplicações utilizadas no mercado.

---

## 🚀 Funcionalidades

* Cadastro de usuário
* Registro de alimentos consumidos
* Registro de exercícios realizados
* Consulta de transações por período
* Cálculo do saldo calórico
* Comunicação via HTTP utilizando JSON
* Consumo de APIs externas
* Armazenamento das informações em memória utilizando `atom`
* Utilização de programação funcional em todas as operações

---

## 🛠 Tecnologias utilizadas

* Clojure 1.11
* Leiningen
* Ring
* Compojure
* Cheshire
* clj-http

---

## 🌐 APIs utilizadas

### USDA FoodData Central

Responsável pela obtenção das informações nutricionais dos alimentos.

Exemplos:

* Banana
* Apple
* Rice
* Milk
* Bread

As calorias são calculadas com base na quantidade informada em gramas.

---

### API Ninjas

Responsável pelo cálculo das calorias gastas durante atividades físicas.

Os cálculos levam em consideração:

* Tipo de atividade
* Duração do exercício
* Peso do usuário
* Idade do usuário
* Sexo do usuário

Exemplos:

* Running
* Walking
* Cycling
* Swimming

---

## 📂 Estrutura do projeto

```
src/
└── calorie_calculator/
    ├── core.clj
    ├── routes.clj
    ├── services.clj
    ├── db.clj
    ├── frontend.clj
    └── frontend_main.clj
```

---

## ▶️ Executando o projeto

### 1. Iniciar o back-end

```bash
lein run
```

O servidor será iniciado em:

```
http://localhost:3001
```

---

### 2. Iniciar o front-end

Em outro terminal, execute:

```bash
lein run -m calorie-calculator.frontend-main
```

---

## 📋 Funcionalidades disponíveis

### Cadastro de usuário

São armazenadas as seguintes informações:

* Altura
* Peso
* Idade
* Sexo

Esses dados são utilizados para personalizar o cálculo das calorias gastas nos exercícios.

---

### Registro de alimentos

São informados:

* Nome do alimento
* Quantidade em gramas
* Data

As calorias são adicionadas ao saldo.

---

### Registro de exercícios

São informados:

* Nome da atividade
* Duração
* Data

As calorias gastas são subtraídas do saldo.

---

### Consulta de transações

Permite visualizar todas as transações realizadas dentro de um intervalo de datas.

---

### Saldo calórico

Calculado pela expressão:

```
Saldo = Calorias Consumidas - Calorias Gastas
```

---

## 🧠 Conceitos de Programação Funcional utilizados

* Funções puras
* Imutabilidade
* Uso de `atom` para gerenciamento de estado
* Mapas e listas
* Operações com `map`
* Operações com `filter`
* Operações com `reduce`
* Separação entre front-end e back-end
* Processamento de dados em formato JSON

---

## 📌 Exemplo de saída

```
[ALIMENTO]
Nome: banana
Quantidade: 50 g
Data: 2026-06-12
Calorias Ganhas: 48.5 kcal

-----------------------------------------

[EXERCÍCIO]
Nome: running
Duração: 30 min
Data: 2026-06-12
Calorias Gastas: 320 kcal
```

---

## 👨‍💻 Autor

Projeto desenvolvido para fins acadêmicos, com o objetivo de aplicar conceitos de Programação Funcional, consumo de APIs externas e construção de aplicações em Clojure.
