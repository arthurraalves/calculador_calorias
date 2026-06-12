(ns calorie-calculator.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [calorie-calculator.routes :refer [app]])
  (:gen-class))

(defn -main []
  (run-jetty app {:port 3001
                  :join? false}))