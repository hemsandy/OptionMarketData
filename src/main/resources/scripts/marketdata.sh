#!/usr/bin/env bash
# set JAVA_HOME
#JAVA_HOME=/usr/java/
# possible arguments to the program BROKER/PUBLISHER/BOTH/SUBSCRIBER
#BOTH starts BROKER and PUBLISHER
#SUBSCRIBER starts a dummy subscriber and consumes all messages
java -cp ./lib/*:./*:. com.wellsfargo.cmt.MarketDataServiceMain BOTH