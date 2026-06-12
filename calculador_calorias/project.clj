(defproject calorie-calculator "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                [ring/ring-core "1.12.1"]
                [ring/ring-jetty-adapter "1.12.1"]
                [ring/ring-json "0.5.1"]
                [compojure "1.7.1"]
                [cheshire "5.13.0"]
                [clj-http "3.13.0"]]
                 
  :main calorie-calculator.core)