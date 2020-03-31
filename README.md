# API для сбербанка
## Использование
* Клонировать проект
* mvn install 
* Подключить как зависимость в нужный проект
* Использовать класс Sberbank

Примеры использования можно найти в классе SberbankTest. Один из них
```
 println("login:")

 // mguid Является переиспользуемым и получается один раз при регистрации девайса
 val mguid = SberbankRegistration().register(readLine()!!) {
                         println("smsPassword:")
                         readLine()!!
                     }
 //Логин нужно выполнять 1 раз на сессию
 val sberbank = SberbankLogining().login(mguid)
 
 val products = sberbank.productList()
 val result = ArrayList<Operation>()
         for (card in products.cards!!.list!!) {
             val elements = sberbank.paymentsList(
                     card,
                     LocalDate.now().minusYears(5),
                     LocalDate.now(),
                     Short.MAX_VALUE.toInt(),
                     0
             ).operations
             if(elements!=null){
                 result.addAll(elements)
             }
         }
  print(result)
 ```

  
