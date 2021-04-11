package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//определение класса MainActivity
public class MainActivity extends AppCompatActivity {
    Button b_1, b_2, b_3, b_4, b_5, b_6, b_7, b_8, b_9, b_0, b_dot, b_equal, b_plus, bmin, bmul, bdiv, bmminus, bmplus, bmr, blog, btan, bcos, bsin, b_left_bracket, b_right_bracket, bac;
    TextView text_view_main, text_view_history;
    ImageView bc;
    double memory_value = 0; // переменная для оперцаий mr, m+, m-
    boolean isResult = false;
    int maxSize = 11;
    @Override
    // Вызывается при входе в "полноценное" состояние
    protected void onCreate(Bundle savedInstanceState) { //onCreate задаёт начальную установку параметров при инициализации активности
        super.onCreate(savedInstanceState);
        // Инициализируйте Активность.
        setContentView(R.layout.activity_main);
        //В метод setContentView() передается ресурс разметки графического интерфейса

        //Метод findViewById() позволяет получить ссылку на View, которая размещена в разметке через его идентификатор.
        b_1 = findViewById(R.id.b_1);
        b_2 = findViewById(R.id.b_2);
        b_3 = findViewById(R.id.b_3);
        b_4 = findViewById(R.id.b_4);
        b_5 = findViewById(R.id.b_5);
        b_6 = findViewById(R.id.b_6);
        b_7 = findViewById(R.id.b_7);
        b_8 = findViewById(R.id.b_8);
        b_9 = findViewById(R.id.b_9);
        b_0 = findViewById(R.id.b_0);

        b_dot = findViewById(R.id.b_dot);
        b_equal = findViewById(R.id.b_equal);
        b_plus = findViewById(R.id.b_plus);
        bmin = findViewById(R.id.bmin);
        bmul = findViewById(R.id.bmul);
        bdiv = findViewById(R.id.bdiv);

        bmminus = findViewById(R.id.bmminus);
        bmplus = findViewById(R.id.bmplus);
        bmr = findViewById(R.id.bmr);

        blog = findViewById(R.id.blog);
        btan = findViewById(R.id.btan);
        bsin = findViewById(R.id.bsin);
        bcos = findViewById(R.id.bcos);

        b_left_bracket = findViewById(R.id.b_left_bracket);
        b_right_bracket = findViewById(R.id.b_right_bracket);
        bc = findViewById(R.id.bc);
        bac = findViewById(R.id.bac);

        text_view_main = findViewById(R.id.text_view_main);
        text_view_history = findViewById(R.id.text_view_history);


        //onclick listeners
        b_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }
                String result = text_view_main.getText() + "1";
                if (result.length() <= maxSize)
                text_view_main.setText(result);
            }
        });
        b_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }
                String result = text_view_main.getText() + "2";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "3";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "4";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "5";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }
                String result = text_view_main.getText() + "6";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "7";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "8";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "9";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "0";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }
                String val = text_view_main.getText().toString();//берём текст, преобразуем в стринг, запишем в переменную val

                if (val.length() == 0) {
                    text_view_main.setText(val + "0");
                }
                int lastPost = val.length() - 1;//индекс последнего элемента
                int chLast = -1;//проверка на ошибку через это
                boolean can_dot = true;//флаг - можно ли ставить точку
                if (!val.isEmpty())
                    chLast = val.charAt(lastPost); //если строка не пуста, берём последний символ
                if (chLast >= '0' && chLast <= '9' || chLast == '.') {
                    while (chLast >= '0' && chLast <= '9' || chLast == '.') {
                        if (chLast == '.') {//если точка
                            can_dot = false;//флаг выходим, ещё
                            break;
                        }
                        if (--lastPost < val.length() & lastPost >= 0) {//берём предпоследний символ, его элемент
                            chLast = val.charAt(lastPost);//уменьшаем индекс последнего элемента
                        } else {

                            chLast = -1; //если последнего символа нет, то while не срабатывает и мы выходим из цикла
                        }

                    }
                    ;
                }
                String result;
                if (can_dot){
                    lastPost = val.length() - 1;//индекс последнего элемента
                    if(!val.isEmpty() && (val.charAt(lastPost) < '0' || val.charAt(lastPost) > '9')) result = text_view_main.getText() + "0.";
                    else result = text_view_main.getText() + "."; //если можно (true) - ставим точку

                    if (result.length() <= maxSize)
                        text_view_main.setText(result);
                }

            }
        });
        bac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view_main.setText("");
                text_view_history.setText("");
            }
        });
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = text_view_main.getText().toString();//берём текст, преобразуем в стринг, запишем в переменную val
                int lastPost = val.length() - 1;
                int chLast = -1;
                if (!val.isEmpty()) chLast = val.charAt(lastPost);
                if (chLast >= 'a' && chLast <= 'z') { //проверяем с конца входят ли данные символы
                    while (chLast >= 'a' && chLast <= 'z') {
                        if (--lastPost < val.length() & lastPost >= 0) {
                            chLast = val.charAt(lastPost); //
                        } else {

                            chLast = -1;
                        }
                        //берём 1 элемент в строке и его позицию, которая сохраняется в pos
                        //++pos увеличиваем, чтобы рассмотреть следующий элемент
                    }
                    ;
                    lastPost++;
                }
                if (!val.isEmpty())
                    val = val.substring(0, lastPost);//если val не пуст, val.substring (берём подстроку) - до предпоследнего значения
                text_view_main.setText(val);//сохраняем изменённую строку
            }
        });
        b_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isResult = false;
                String val = text_view_main.getText().toString();

                if (val.length() == 0) {
                    text_view_main.setText(val + "0+");
                } else {
                    if((val+'+').length() <= maxSize)
                        text_view_main.setText(val + "+");

                }

            }
        });
        bmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isResult = false;
                String val = text_view_main.getText().toString();
                if (val.length() == 0) {
                    text_view_main.setText(val + "0-");
                } else {
                    if((val+'-').length() <= maxSize)
                        text_view_main.setText(val + "-");
                }
            }
        });
        bmul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isResult = false;
                String val = text_view_main.getText().toString();
                if (val.length() == 0) {
                    text_view_main.setText(val + "0×");
                } else {

                    if((val+'×').length() <= maxSize)
                        text_view_main.setText(val + "×");
                }

            }
        });
        bdiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isResult = false;
                String val = text_view_main.getText().toString();
                if (val.length() == 0) {
                    text_view_main.setText(val + "0÷");
                } else {

                    if((val+'÷').length() <= maxSize)
                        text_view_main.setText(val + "÷");
                }
            }
        });
        bmminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = text_view_main.getText().toString();
                String replacedstr = val.replace('÷', '/').replace('×', '*');
                if (replacedstr.isEmpty()) {
                    text_view_history.setText("");
                    text_view_main.setText("0");
                } else {
                    try {
                        while (replacedstr.charAt(replacedstr.length() - 1) == '*' || replacedstr.charAt(replacedstr.length() - 1) == '/')
                            replacedstr = replacedstr.substring(0, replacedstr.length() - 1);
                        double result = eval(replacedstr);
                        memory_value -= result;
                        text_view_main.setText(String.valueOf(result));
                        text_view_history.setText(val);
                        isResult = true;
                    } catch (Exception e) {
                        text_view_history.setText("Error");
                        text_view_main.setText("");

                    }
                }
            }
        });
        b_left_bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "(";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_right_bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + ")";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);

            }
        });

        bsin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "sin";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        bcos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "cos";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        btan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isResult){
                    text_view_history.setText(text_view_main.getText());
                    text_view_main.setText("");
                    isResult = false;
                }

                String result = text_view_main.getText() + "tan";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });

        bmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_view_main.setText(String.valueOf(memory_value));//выводим значение из памяти
                text_view_history.setText("");// в истории делаем пустой текст
                isResult = true;
            }
        });
        bmplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = text_view_main.getText().toString();
                String replacedstr = val.replace('÷', '/').replace('×', '*');
                if (replacedstr.isEmpty()) {
                    text_view_history.setText("");
                    text_view_main.setText("0");
                } else {
                    try {
                        while (replacedstr.charAt(replacedstr.length() - 1) == '*' || replacedstr.charAt(replacedstr.length() - 1) == '/')
                            replacedstr = replacedstr.substring(0, replacedstr.length() - 1);
                        double result = eval(replacedstr);
                        memory_value += result;
                        text_view_main.setText(String.valueOf(result));
                        text_view_history.setText(val);
                        isResult = true;
                    } catch (Exception e) {
                        text_view_history.setText("Error");
                        text_view_main.setText("");

                    }
                }
            }
        });

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = text_view_main.getText() + "log";
                if (result.length() <= maxSize)
                    text_view_main.setText(result);
            }
        });
        b_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = text_view_main.getText().toString();
                String replacedstr = val.replace('÷', '/').replace('×', '*');
                if (replacedstr.isEmpty()) {
                    text_view_history.setText("");
                    text_view_main.setText("0");
                } else {
                    try {
                        while (replacedstr.charAt(replacedstr.length() - 1) == '*' || replacedstr.charAt(replacedstr.length() - 1) == '/')
                            replacedstr = replacedstr.substring(0, replacedstr.length() - 1);
                        double result = eval(replacedstr);
                        text_view_main.setText(String.valueOf(result));
                        text_view_history.setText(val);
                        isResult = true;
                    } catch (Exception e) {
                        text_view_history.setText("Error");
                        text_view_main.setText("");

                    }
                }


            }
        });

    }


    //eval функция ()
    public static double eval(final String str) {
        return new Object() {
            //конструктор для инициализайии объекта
            int pos = -1, ch;// pos нужен для разбора строки - отделение аргументов(цифр) и операторов (+,-,) друг от друга

            //начальная позиция для элементов , которая используется так: если мы передаём пустую строку и нажимаем = , то бросится ошибка тк позиция элемента не заменилась
            void nextChar() {
                if (++pos < str.length()) {
                    ch = str.charAt(pos);
                } else {
                    ch = -1;
                }
                //берём 1 элемент в строке и его позицию, которая сохраняется в pos
                //++pos увеличиваем, чтобы рассмотреть следующий элемент
            }



            boolean eat(int charToEat) { //соответствует ли текущий символ тому, что мы ему передали , если да - True
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // сложение
                    else if (eat('-')) x -= parseTerm(); // вычитание
                    else if (eat(')')) {
                        while (eat(')')) ;
                    } else if (pos < str.length()) {
                        x *= parseFactor();
                    }
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // умножение
                    else if (eat('/')) {
                        double res = parseFactor();
                        if (res == 0) throw new RuntimeException("Zero division by zero");

                        x /= res; // деление

                    } else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // унарный плюс
                if (eat('-')) return -parseFactor(); // унарный минус

                double x;
                int startPos = this.pos;
                if (eat('(')) { // скобочки
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.' || ch=='E') { // циферки
                    while ((ch >= '0' && ch <= '9') || ch == '.' || ch=='E') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // символы для распознания функций
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    if (str.length() == this.pos) throw new RuntimeException("Not set last element");
                    x = parseFactor();
                    if (func.equals("sin")) x = Math.round(Math.sin(Math.toRadians(x)));
                    else if (func.equals("cos")) x = Math.round(Math.cos(Math.toRadians(x)));
                    else if (func.equals("tan")) x = Math.round(Math.tan(Math.toRadians(x)));
                    else if (func.equals("log")) x = Math.round(Math.log10(x));

                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    x = 0;
                }


                return x;
            }
        }.parse();
    }
}