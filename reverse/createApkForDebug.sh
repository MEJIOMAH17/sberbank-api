#!/usr/bin/env bash
#в агрументы запуска sh передавать путь до сертификата

apktool d -f -r  Sberbank.apk
cp $1 Sberbank/res/raw/thawte_rsa_ca_2018_thawte_ssl_wildcard.cer
apkName="sberbank_pathed.apk"
apktool b Sberbank -o ${apkName}
java -jar sign.jar ${apkName}
rm ${apkName}