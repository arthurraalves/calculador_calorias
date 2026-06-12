(ns calorie-calculator.routes
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :as json]
            [ring.middleware.json :refer [wrap-json-body]]
            [calorie-calculator.db :refer [database]]
            [calorie-calculator.services :as services]))

(defroutes app-routes

  ;; Teste da API
  (GET "/" []
    "API funcionando")

  ;; Retorna todas as transações
  (GET "/transactions" [start end]
  (let [transactions (:transactions @database)
        result (if (and start end)
                 (services/transactions-by-period
                  transactions
                  start
                  end)
                 transactions)]

    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string result)}))

  ;; Cadastra os dados do usuário
  (POST "/user" request
    (let [user-data (:body request)]
      (swap! database assoc :user user-data)

      {:status 200
       :headers {"Content-Type" "application/json"}
       :body (json/generate-string
              {:message "Usuário cadastrado com sucesso"})}))

  (GET "/user" []
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string
          (:user @database))})

  (POST "/food" request
  (let [food-data (:body request)

        calories
        (services/get-food-calories
         (:name food-data)
         (:quantity food-data))

        transaction
        (assoc food-data
               :calories calories
               :type "food")]

    (swap! database services/add-transaction transaction)

    {:status 200
     :headers {"Content-Type" "application/json"}
     :body
     (json/generate-string
      {:message "Alimento registrado com sucesso"})}))

  (POST "/exercise" request
  (let [exercise-data (:body request)

        user-data (:user @database)

        weight (:weight user-data)
        age (:age user-data)
        gender (:sex user-data)

        calories
        (services/get-exercise-calories
         (:name exercise-data)
         (:duration exercise-data)
         weight
         age
         gender)

        transaction
        (-> exercise-data
            (assoc :calories (- calories))
            (assoc :type "exercise"))]

    (swap! database services/add-transaction transaction)

    {:status 200
     :headers {"Content-Type" "application/json"}
     :body (json/generate-string
             {:message "Exercício registrado com sucesso"})}))


  (GET "/balance" [start end]
  (let [transactions (:transactions @database)
        filtered-transactions
        (if (and start end)
          (services/transactions-by-period
           transactions
           start
           end)
          transactions)]

    {:status 200
     :headers {"Content-Type" "application/json"}
     :body
     (json/generate-string
      {:balance
       (services/calculate-balance
        filtered-transactions)})}))

  (route/not-found "Página não encontrada"))

(def app
  (wrap-json-body app-routes {:keywords? true}))