(ns hippy.core
  (:use [hippy.xmpp]))


(defn -main [& args]
  (startBot)
  (Thread/sleep 50000)
  )
