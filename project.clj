(defproject sortd "0.1.0-SNAPSHOT"
  :description "Sortd sorts simple delimited records."
  :url "http://google.com"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [org.clojure/tools.cli "1.0.194"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [ring-server "0.5.0"]]
  :plugins [[lein-cloverage "1.1.2"]
            [lein-ring "0.12.5"]]
  :ring {:handler sortd.handler/app
         :init sortd.handler/init
         :destroy sortd.handler/destroy}
  :main sortd.main
  :ns-exclude-regex [#"sortd.repl"]
  :profiles {:uberjar {:aot :all}
             :production {:ring {:open-browser? false
                                 :stacktraces? false
                                 :auto-reload? false}}
             :dev {:dependencies [[ring/ring-mock "0.4.0"]
                                  [ring/ring-devel "1.7.1"]]}})
