import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Базовый класс Settlement
class Settlement {
    protected long amountCitizens;
    protected long treasury;
    protected List<String> tradeRelations;
    protected String settlementName;

    public Settlement(long amountCitizens, String settlementName) {
        this.amountCitizens = amountCitizens;
        this.settlementName = settlementName;
        this.treasury = 0;
        this.tradeRelations = new ArrayList<>();
    }

    public void addTreasury(long amount) {
        treasury += amount;
        System.out.println("В " + settlementName + " теперь " + treasury + " золота");
    }

    public void removeTreasury(long amount) {
        if (amount > treasury) {
            System.out.println("Казна " + settlementName + " пуста");
            treasury = 0;
        } else {
            treasury -= amount;
            System.out.println("В " + settlementName + " теперь " + treasury + " золота");
        }
    }

    public void setTradeRelation(String otherSettlementName) {
        tradeRelations.add(otherSettlementName);
    }

    public void displayTradeRelations() {
        System.out.println("Торговые отношения " + settlementName + " установлены с:");
        for (String relation : tradeRelations) {
            System.out.println(relation);
        }
    }
}

// Класс Castle наследуется от Settlement
class Castle extends Settlement {
    private long _amountArmy;
    private List<String> warCastles;
    private List<Castle> otherCastles;
    private List<String> relationsCastles;
    private Scanner scanner;

    public Castle(long amountCitizens, String castleName) {
        super(amountCitizens, castleName);
        this._amountArmy = 0;
        this.warCastles = new ArrayList<>();
        this.otherCastles = new ArrayList<>();
        this.relationsCastles = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addArmy(long amount) {
        _amountArmy += amount;
        System.out.println("В замке " + settlementName + " теперь " + _amountArmy + " солдат");
    }

    public void removeArmy(long amount) {
        if (amount > _amountArmy) {
            System.out.println("Нельзя убрать солдат больше, чем есть в замке " + settlementName);
        } else {
            _amountArmy -= amount;
            System.out.println("В замке " + settlementName + " теперь " + _amountArmy + " солдат");
        }
    }

    public void executeCitizens(int amount) {
        if (amount > amountCitizens) {
            System.out.println("Нельзя казнить больше граждан, чем есть в замке " + settlementName);
        } else if (amount < 0) {
            System.out.println("Количество граждан для казни должно быть больше нуля");
        } else {
            amountCitizens -= amount;
            System.out.println("В замке " + settlementName + " теперь " + amountCitizens + " граждан");
        }
    }

    public void capitulate() {
        amountCitizens = 0;
        _amountArmy = 0;
        treasury = 0;
        System.out.println("Замок " + settlementName + " капитулировал");
    }

    public void addOtherCastle(Castle castle) {
        otherCastles.add(castle);
    }

    public void displayOtherCastles() {
        System.out.println("Список других замков:");
        for (Castle castle : otherCastles) {
            System.out.println(castle.settlementName);
        }
    }

    public void announceWar(String enemyCastleName) {
        boolean found = false;
        for (Castle castle : otherCastles) {
            if (castle.settlementName.equals(enemyCastleName)) {
                if (warCastles.contains(enemyCastleName)) {
                    System.out.println("Замок " + settlementName + " уже ведет войну с замком " + enemyCastleName);
                } else {
                    warCastles.add(enemyCastleName);
                    System.out.println("Замок " + settlementName + " объявил войну замку " + enemyCastleName);
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Замок с названием " + enemyCastleName + " не найден в списке");
        }
    }

    public void displayWarCastles() {
        System.out.println("Замок " + settlementName + " ведет войну с:");
        for (String warCastle : warCastles) {
            System.out.println(warCastle);
        }
    }

    public void makePeace(String enemyCastleName) {
        boolean found = false;
        for (String castle : warCastles) {
            if (castle.equals(enemyCastleName)) {
                warCastles.remove(enemyCastleName);
                System.out.println("Замок " + settlementName + " заключил мир с замком " + enemyCastleName);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Замок " + settlementName + " не ведет войну с замком " + enemyCastleName);
        }
    }

    public void addArmyOrRemove(long amountSolders) {
        if (amountSolders < 0 && amountSolders > _amountArmy) {
            System.out.println("Нельзя убрать солдат больше, чем есть по факту");
            return;
        }
        _amountArmy += amountSolders;
        System.out.println("В вашем замке " + settlementName + " теперь " + _amountArmy + " солдат");
        checkCapitulation();
    }

    public void addTreasuryOrRemove(long amountGold) {
        treasury += amountGold;
        if (treasury < 0) {
            System.out.println("Казна и так пуста");
            treasury = 0;
            return;
        }
        System.out.println("В вашем замке " + settlementName + " теперь " + treasury + " золота");
        checkCapitulation();
    }

    public void doExecute(int amountExecutedCitizens) {
        if (amountExecutedCitizens > amountCitizens) {
            System.out.println("Нельзя казнить столько людей, в замке " + settlementName + " всего " + amountCitizens + " граждан");
            return;
        } else if (amountExecutedCitizens < 0) {
            System.out.println("Количество людей на казнь должно быть больше нуля");
            return;
        }
        amountCitizens -= amountExecutedCitizens;
        System.out.println("В замке " + settlementName + " теперь " + amountCitizens + " граждан");
        checkCapitulation();
    }

    public void displayTradeRelations() { //~~~функция для вывода текущих торговых отношений~~~
        System.out.println("Торговые отношения установлены с следующими королевствами:");
        for (String castle : relationsCastles) {
            System.out.println(castle);
        }
    }

    public void removeTradeRelations() { //~~~функция для разрыва торговых отношений~~~
        System.out.println("Выберите номер королевства с которым хотите разорвать торги");
        byte i = 0;
        for (String castle : relationsCastles) {
            System.out.println(i + ". " + castle);
            i++;
        }
        byte numberCastle = scanner.nextByte();
        if (numberCastle >= 0 && numberCastle < relationsCastles.size()) {
            relationsCastles.remove(numberCastle);
        } else {
            System.out.println("Неверный номер королевства");
        }
    }

    private void checkCapitulation() {
        if (amountCitizens == 0 && _amountArmy == 0 && treasury == 0) {
            capitulate();
            System.out.println("Выход из программы, ваш замок капитулировал.");
            System.exit(0);
        }
    }
}

// Класс для работы с пользовательским вводом
class UserInput {
    private Scanner scanner;

    public UserInput() {
        scanner = new Scanner(System.in);
    }

    public int getIntInput(String message) {
        System.out.print(message);
        return scanner.nextInt();
    }

    public long getLongInput(String message) {
        System.out.print(message);
        return scanner.nextLong();
    }

    public String getStringInput(String message) {
        System.out.print(message);
        return scanner.next();
    }
}

// Главный класс приложения
public class Main {
    public static void main(String[] args) {
        UserInput userInput = new UserInput();
        Castle castle = new Castle(userInput.getLongInput("Введите количество граждан: "),
                userInput.getStringInput("Введите название замка: "));

        int numOtherCastles = userInput.getIntInput("Введите количество других замков: ");
        for (int i = 0; i < numOtherCastles; i++) {
            long citizens = userInput.getLongInput("Введите количество граждан для замка " + (i + 1) + ": ");
            String name = userInput.getStringInput("Введите название замка " + (i + 1) + ": ");
            Castle otherCastle = new Castle(citizens, name);
            castle.addOtherCastle(otherCastle);
        }

        castle.displayOtherCastles();

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Объявить войну");
            System.out.println("2. Заключить мир");
            System.out.println("3. Добавить/убрать солдат");
            System.out.println("4. Добавить/убрать золото");
            System.out.println("5. Казнить граждан");
            System.out.println("6. Вывести торговые отношения");
            System.out.println("7. Разорвать торговые отношения");
            System.out.println("8. Выход");

            int choice = userInput.getIntInput("");
            switch (choice) {
                case 1:
                    System.out.println("Введите название замка для объявления войны:");
                    String enemyCastle = userInput.getStringInput("");
                    castle.announceWar(enemyCastle);
                    break;
                case 2:
                    System.out.println("Введите название замка для заключения мира:");
                    String peaceCastle = userInput.getStringInput("");
                    castle.makePeace(peaceCastle);
                    break;
                case 3:
                    System.out.println("Введите количество солдат для добавления или удаления (отрицательное число для удаления):");
                    long amountSolders = userInput.getLongInput("");
                    castle.addArmyOrRemove(amountSolders);
                    break;
                case 4:
                    System.out.println("Введите количество золота для добавления или удаления (отрицательное число для удаления):");
                    long amountGold = userInput.getLongInput("");
                    castle.addTreasuryOrRemove(amountGold);
                    break;
                case 5:
                    System.out.println("Введите количество граждан для казни:");
                    int amountExecutedCitizens = userInput.getIntInput("");
                    castle.doExecute(amountExecutedCitizens);
                    break;
                case 6:
                    castle.displayTradeRelations();
                    break;
                case 7:
                    castle.removeTradeRelations();
                    break;
                case 8:
                    System.out.println("Выход из приложения");
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте еще раз");
            }

            castle.displayWarCastles();
        }
    }
}
