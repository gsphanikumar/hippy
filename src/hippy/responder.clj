(ns hippy.responder
(:import [org.jivesoftware.smack.packet Message]))


(declare getResponse)

(defn messageResponder [ch message]
  (if-not (clojure.string/blank? (.getBody message))
   (if-not (= (.getFrom message) "Hippy Bot" )
     (.sendMessage ch (getResponse message)  )))
  )

(defn getResponse [message]
"sample"
)


(defn packetResponder [ch packet]
  ( (messageResponder ch packet )))

