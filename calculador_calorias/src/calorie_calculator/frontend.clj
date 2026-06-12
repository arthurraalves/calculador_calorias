(ns calorie-calculator.frontend
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

(defn register-user []
  (println)
  (println "=== Cadastro do usuario ===")

  (print "Altura (cm): ")
  (flush)
  (let [height (Integer/parseInt (read-line))

        _ (print "Peso (kg): ")
        _ (flush)
        weight (Integer/parseInt (read-line))

        _ (print "Idade: ")
        _ (flush)
        age (Integer/parseInt (read-line))

        _ (print "Sexo (M/F): ")
        _ (flush)
        sex (read-line)]

    (client/post
      "http://localhost:3001/user"
      {:content-type :json
       :body (json/generate-string
               {:height height
                :weight weight
                :age age
                :sex sex})})

    (println)
    (println "Usuario cadastrado com sucesso!")))


(defn register-food []
  (println)
  (println "=== Registro de alimento ===")

  (print "Nome do alimento: ")
  (flush)
  (let [name (read-line)

        _ (print "Quantidade (g): ")
        _ (flush)
        quantity (Integer/parseInt (read-line))

        _ (print "Data (AAAA-MM-DD): ")
        _ (flush)
        date (read-line)]

    (client/post
     "http://localhost:3001/food"
     {:content-type :json
      :body
      (json/generate-string
       {:name name
        :quantity quantity
        :date date})})

    (println)
    (println "Alimento registrado com sucesso!")))


(defn register-exercise []
  (println)
  (println "=== Registro de exercicio ===")

  (print "Nome do exercicio: ")
  (flush)
  (let [name (read-line)

        _ (print "Duracao (min): ")
        _ (flush)
        duration (Integer/parseInt (read-line))

        _ (print "Data (AAAA-MM-DD): ")
        _ (flush)
        date (read-line)]

    (client/post
     "http://localhost:3001/exercise"
     {:content-type :json
      :body
      (json/generate-string
       {:name name
        :duration duration
        :date date})})

    (println)
    (println "Exercicio registrado com sucesso!")))


(defn show-transactions []
  (println)
  (println "================ EXTRATO ================")

  (print "Data inicial (AAAA-MM-DD): ")
  (flush)

  (let [start (read-line)

        _ (print "Data final (AAAA-MM-DD): ")
        _ (flush)
        end (read-line)

        response
        (client/get
         "http://localhost:3001/transactions"
         {:query-params
          {:start start
           :end end}})

        transactions
        (json/parse-string
         (:body response)
         true)]

    (if (empty? transactions)

      (println "\nNenhuma transacao encontrada.")

      (doall
       (map
        (fn [transaction]

          (if (= (:type transaction) "food")

            (do
              (println)
              (println "[ALIMENTO]")
              (println "Nome:" (:name transaction))
              (println "Quantidade:" (:quantity transaction) "g")
              (println "Data:" (:date transaction))
              (println "Calorias Ganhas:" (:calories transaction) "kcal"))

            (do
              (println)
              (println "[EXERCICIO]")
              (println "Nome:" (:name transaction))
              (println "Duracao:" (:duration transaction) "min")
              (println "Data:" (:date transaction))
              (println "Calorias Perdidas:" (- (:calories transaction)) "kcal")))

          (println "\n-----------------------------------------"))

        transactions))))

  (println "\n========================================="))


(defn show-balance []
  (println)
  (println "========== SALDO ==========")

  (print "Data inicial (AAAA-MM-DD): ")
  (flush)
  (let [start (read-line)

        _ (print "Data final (AAAA-MM-DD): ")
        _ (flush)
        end (read-line)

        response
        (client/get
         "http://localhost:3001/balance"
         {:query-params
          {:start start
           :end end}})

        balance
        (json/parse-string
         (:body response)
         true)]

    (println)
    (println "Periodo:" start "ate" end)
    (println)
    (println "Saldo de calorias:" (:balance balance) "kcal")
    (println)
    (println "==========================")))

(defn show-menu []
  (println)
  (println "===== CALCULADORA DE CALORIAS =====")
  (println "1 - Cadastrar usuario")
  (println "2 - Registrar alimento")
  (println "3 - Registrar exercicio")
  (println "4 - Ver transacoes")
  (println "5 - Ver saldo")
  (println "6 - Sair")
  (println)
  (print "Escolha uma opcao: ")
  (flush))


(defn main []
  (show-menu)

  (let [option (read-line)]

    (case option
      "1" (register-user)
      "2" (register-food)
      "3" (register-exercise)
      "4" (show-transactions)
      "5" (show-balance)
      "6" (println "Encerrando...")

      (println "Opcao invalida"))

    (when-not (= option "6")
      (main))))