package com.javarush.test.level17.lesson10.bonus02;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* CRUD 2
CrUD Batch - multiple Creation, Updates, Deletion
!!!РЕКОМЕНДУЕТСЯ выполнить level17.lesson10.bonus01 перед этой задачей!!!

Программа запускается с одним из следующих наборов параметров:
-c name1 sex1 bd1 name2 sex2 bd2 ...
-u id1 name1 sex1 bd1 id2 name2 sex2 bd2 ...
-d id1 id2 id3 id4 ...
-i id1 id2 id3 id4 ...
Значения параметров:
name - имя, String
sex - пол, "м" или "ж", одна буква
bd - дата рождения в следующем формате 15/04/1990
-с  - добавляет всех людей с заданными параметрами в конец allPeople, выводит id (index) на экран в соответствующем порядке
-u  - обновляет соответствующие данные людей с заданными id
-d  - производит логическое удаление всех людей с заданными id
-i  - выводит на экран информацию о всех людях с заданными id: name sex bd

id соответствует индексу в списке
Формат вывода даты рождения 15-Apr-1990
Все люди должны храниться в allPeople
Порядок вывода данных соответствует вводу данных
Обеспечить корректную работу с данными для множества нитей (чтоб не было затирания данных)
Используйте Locale.ENGLISH в качестве второго параметра для SimpleDateFormat
*/

public class Solution {
    public static List<Person> allPeople = new ArrayList<Person>();
    static {
        allPeople.add(Person.createMale("Иванов Иван", new Date()));  //сегодня родился    id=0
        allPeople.add(Person.createMale("Петров Петр", new Date()));  //сегодня родился    id=1
    }

    public static void main(String[] args) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

            if (args[0].equals("-c")) {
                addPersons(args, dateFormat);
            } else if (args[0].equals("-u")) {
                refreshDataOfPersons(args, dateFormat);
            } else if (args[0].equals("-d")) {
                deletePersonsByID(args);
            } else if (args[0].equals("-i")) {
                printInfoPersonsByID(args, dateFormat);
            }
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void printInfoPersonsByID(String[] args, SimpleDateFormat dateFormat) {

        for (int index = 1; index < args.length; index++) {
            dateFormat = new SimpleDateFormat("dd-MMM-YYYY", Locale.ENGLISH);
            System.out.println(allPeople.get(Integer.parseInt(args[index])).getName() + " " +
                    (allPeople.get(Integer.parseInt(args[index])).getSex().equals(Sex.MALE) ? "м" : "ж") + " " +
                    dateFormat.format(allPeople.get(Integer.parseInt(args[index])).getBirthDay()));
        }
    }

    private static void deletePersonsByID(String[] args) {

        for (int index = 1; index < args.length; index++)
            allPeople.remove(allPeople.get(Integer.parseInt(args[index])));
    }

    private static void refreshDataOfPersons(String[] args, SimpleDateFormat dateFormat) throws ParseException {

        for (int index = 1; index < args.length - 3; index += 4) {
            allPeople.get(Integer.parseInt(args[index])).setName(args[index + 1]);
            if (args[index + 2].equals("м"))
                allPeople.get(Integer.parseInt(args[index])).setSex(Sex.MALE);
            else if (args[index + 2].equals("ж"))
                allPeople.get(Integer.parseInt(args[index])).setSex(Sex.FEMALE);
            allPeople.get(Integer.parseInt(args[index])).setBirthDay(dateFormat.parse(args[4]));
        }
    }

    private static void addPersons(String[] args, SimpleDateFormat dateFormat) throws ParseException {

        for (int index = 1; index < args.length - 2; index += 3) {
            Person person;
            if (args[index + 1].equals("м")) {
                person = Person.createMale(args[index], dateFormat.parse(args[index + 2]));
            } else {
                person = Person.createFemale(args[index], dateFormat.parse(args[index + 2]));
            }
            allPeople.add(person);
            System.out.println(allPeople.indexOf(person));
        }
    }
}
