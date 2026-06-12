(ns calorie-calculator.services
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as str]))

;; ==========================
;; Configuração das APIs
;; ==========================

(def api-key "zPM9Ytj37xE27rE8akro5DXIrD6irNegKWKcZOno")

(def usda-api-key "gnn6vw0nsKcDXj25XNacOhYgR7v8VFUPP6OkBy1V")

;; ==========================
;; Manipulação de transações
;; ==========================

(defn add-transaction
  [db transaction]
  (update db :transactions conj transaction))

(defn calculate-balance
  [transactions]
  (reduce + (map :calories transactions)))

(defn transactions-by-period
  [transactions start-date end-date]
  (filter
   #(and
      (>= (compare (:date %) start-date) 0)
      (<= (compare (:date %) end-date) 0))
   transactions))

;; ==========================
;; API de alimentos
;; ==========================

(defn get-food-calories
  [food-name grams]

  (let [response
        (client/get
         "https://api.nal.usda.gov/fdc/v1/foods/search"
         {:query-params
          {"api_key" usda-api-key
           "query" food-name
           "pageSize" 10}})

        body
        (json/parse-string (:body response) true)

        food
        (or
         (first
          (filter
           #(str/includes?
             (str/lower-case (:description %))
             "raw")
           (:foods body)))

         (first (:foods body)))]

    (println "Alimento escolhido:" (:description food))

    (if food

      (let [nutrients (:foodNutrients food)

            calories
            (some
             #(when (= (:nutrientName %) "Energy")
                (:value %))
             nutrients)]

        (* (or calories 0)
           (/ grams 100.0)))

      0)))

;; ==========================
;; API de exercícios
;; ==========================

(defn get-exercise-calories
  [exercise-name duration weight age gender]

  (let [response
        (client/get
         "https://api.api-ninjas.com/v1/caloriesburned"
         {:headers {"X-Api-Key" api-key}
          :query-params {"activity" exercise-name
                         "duration" duration
                         "weight" weight
                         "age" age
                         "gender" gender}})

        body
        (json/parse-string (:body response) true)]

    (:total_calories (first body))))