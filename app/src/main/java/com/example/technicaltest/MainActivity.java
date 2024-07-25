package com.example.technicaltest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btnProcess;
    EditText etNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view_pascal);
        btnProcess = findViewById(R.id.btn_process);
        etNumber = findViewById(R.id.et_id);


        btnProcess.setOnClickListener(view -> {
            int number = Integer.parseInt(etNumber.getText().toString());

            String result = getInvertedPascalTriangle(number);

            textView.setText(result);
        });

    }

    public String getInvertedPascalTriangle(int n) {
        StringBuilder sb = new StringBuilder();
        int[][] triangle = new int[n][];

        // Build Pascal's Triangle
        for (int i = 0; i < n; i++) {
            triangle[i] = new int[i + 1];
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }

        // Find the width of the largest row
        int maxWidth = (n * 2) - 1; // Approximate width

        // Build the string with center alignment
        for (int i = n - 1; i >= 0; i--) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < triangle[i].length; j++) {
                row.append(triangle[i][j]).append(" ");
            }
            // Center-align the row
            String rowString = row.toString();
            int spaces = (maxWidth - rowString.length()) / 2;
            sb.append("  ".repeat(Math.max(0, spaces))); // Add leading spaces
            sb.append(rowString.trim()).append("\n");
        }

        return sb.toString();
    }
}