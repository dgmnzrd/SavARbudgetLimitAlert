package com.example.appalertbudgetlimit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText etPresupuesto, etProducto, etPrecio;
    private Button btnAgregar;
    private TextView tvTotal;
    private RecyclerView rvListaProductos;
    private double presupuesto = 0.0;
    private double total = 0.0;
    private List<Product> listaProductos;
    private ProductosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etPresupuesto = findViewById(R.id.etPresupuesto);
        etProducto = findViewById(R.id.etProducto);
        etPrecio = findViewById(R.id.etPrecio);
        btnAgregar = findViewById(R.id.btnAgregar);
        tvTotal = findViewById(R.id.tvTotal);
        rvListaProductos = findViewById(R.id.rvListaProductos);

        // Inicializar la lista y el adaptador
        listaProductos = new ArrayList<>();
        adapter = new ProductosAdapter(listaProductos);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(this));
        rvListaProductos.setAdapter(adapter);

        // Función para agregar productos
        btnAgregar.setOnClickListener(v -> agregarProducto());

        // Escuchar cambios en el campo del presupuesto
        etPresupuesto.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String presupuestoInput = etPresupuesto.getText().toString();
                if (!presupuestoInput.isEmpty()) {
                    presupuesto = Double.parseDouble(presupuestoInput);
                }
            }
        });
    }

    private void agregarProducto() {
        String nombreProducto = etProducto.getText().toString();
        String precioInput = etPrecio.getText().toString();

        if (!nombreProducto.isEmpty() && !precioInput.isEmpty()) {
            double precio = Double.parseDouble(precioInput);

            // Agregar el producto a la lista
            listaProductos.add(new Product(nombreProducto, precio));
            adapter.notifyDataSetChanged();

            // Actualizar el total
            total += precio;
            tvTotal.setText("Total: $" + total);

            // Verificar si se excedió el presupuesto
            verificarPresupuesto();
        }
    }

    private void verificarPresupuesto() {
        if (presupuesto > 0) {
            double limiteAlerta = presupuesto * 0.9; // 90% del presupuesto

            if (total > presupuesto) {
                mostrarAlerta("¡Presupuesto Excedido!", "Has excedido el presupuesto definido.");
            } else if (total >= limiteAlerta) {
                mostrarAlerta("Cuidado", "Estás cerca de exceder el presupuesto.");
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss()) // Cierra el diálogo al hacer clic en OK
                .setIcon(android.R.drawable.ic_dialog_alert) // Ícono de alerta (opcional)
                .setCancelable(false) // No se puede cancelar tocando fuera del diálogo
                .show();
    }
}