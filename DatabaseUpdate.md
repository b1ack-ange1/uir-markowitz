Если указанный функционал реализован, то второй раз его естественно не нужно реализовывать

  1. в классе AuthData - метод получить список его портфелей - getPortfolios()
  1. в классе AuthData - метод получить объект по логину - getObjectByLogin(String login)
  1. в классе тикер - класс-метод получить список всех тикеров - getAllTickers()
  1. в классе UserPortfolio - метод получения списка всех входящих тикеров и их весов - getComposition()
  1. в классе портфель - метод получения списка всех ковариаций входящих тикеров - getCovariances()
  1. в классе тикер - метод получения значений доходностей за временной интервал - getProfits(TimeStamp from, TimeStamp to)
  1. в классе тикер - метод получения значений котировок за временной интервал - getPrices(TimeStamp from, TimeStamp to)
  1. метод добавления портфеля пользователю - addPortfolio(UserPortfolio portfolio)
  1. добавление тикера в портфель addTicker(Ticker ticker, Double weight)