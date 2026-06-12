(ns calorie-calculator.db)

(def database
  (atom
   {:user {}
    :transactions '()}))