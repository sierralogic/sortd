(ns sortd.util)

(defn ->str
  "Converts `x` to string.  Passes through `x` `nil` as `nil`."
  [x]
  (when x
    (if (keyword? x)
      (-> x
          str
          (subs 1))
      (str x))))
