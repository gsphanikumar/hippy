(ns hippy.xmpp
  (:import [org.jivesoftware.smack
            Chat ChatManager MessageListener ChatManagerListener PacketListener XMPPConnection XMPPException ]
           [org.jivesoftware.smack.packet
            Message Presence]
           [org.jivesoftware.smackx.muc InvitationListener MultiUserChat] )
  (:use [hippy.responder])
  )

(def con (XMPPConnection. "chat.hipchat.com"))


(defn startBot []
  (println "Starting bot")
  (.connect con)
  (.login con "username" "password" "bot")
  (println (.isAuthenticated con))
  (.addChatListener (.getChatManager con)
                    (proxy [ChatManagerListener] []
                      (chatCreated [chat locally]
                        (.addMessageListener chat (proxy [MessageListener] []
                                                    (processMessage [ch message]
                                                      (messageResponder ch message)
                                                      )  ) ))
                      ))

  ( MultiUserChat/addInvitationListener con (proxy [InvitationListener] []
                                              (invitationReceived [con room arg2 arg3 arg4 arg5 ]
                                                (println "Received invite")
                                                (def muc (MultiUserChat. con room))
                                                (.join muc "Hippy Bot")
                                                (.addMessageListener muc (proxy [PacketListener] []
                                                                           (processPacket [packet]
                                                                             (packetResponder muc packet)))
                                                                     )
                                                )))
  )
