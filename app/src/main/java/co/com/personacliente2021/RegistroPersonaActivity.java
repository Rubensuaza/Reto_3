package co.com.personacliente2021;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.com.personacliente2021.dto.PersonaDTO;
import co.com.personacliente2021.model.Persona;
import co.com.personacliente2021.service.persona.PersonaServiceImpl;
import co.com.personacliente2021.service.tipodocumento.TipoDocumentoServiceImpl;
import co.com.personacliente2021.util.ActionBarUtil;

public class RegistroPersonaActivity extends AppCompatActivity {

    @BindView(R.id.txt_documento)
    EditText txtDocumento;

    @BindView(R.id.txt_nombre)
    EditText txtNombre;

    @BindView(R.id.txt_apellido)
    EditText txtApellido;

    @BindView(R.id.spinnerTipoDocumento)
    Spinner spinnerTipoDocumento;

    PersonaDTO persona;
    private Integer documentoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_persona);
        ButterKnife.bind(this);
        persona = new PersonaDTO();
        persona= (PersonaDTO) getIntent().getSerializableExtra("persona");
        if(persona!=null){
            spinnerTipoDocumento.setSelection(persona.getIdTipoDocumento());
            txtDocumento.setText(persona.getNumeroDocumento());
            txtNombre.setText(persona.getNombre());
            txtApellido.setText(persona.getApellido());
        }


        ActionBarUtil.getInstance(this, true).setToolBar(getString(R.string.registro_persona), getString(R.string.insertar));
        listarTiposDocumentos();
        onSelectItemSpinner();
    }

    private void onSelectItemSpinner() {
        spinnerTipoDocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 documentoSeleccionado = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listarTiposDocumentos() {
        TipoDocumentoServiceImpl tipoDocumentoService = new TipoDocumentoServiceImpl(this);
        tipoDocumentoService.getTipoDocumento(spinnerTipoDocumento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            guardarInformacion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void guardarInformacion(){
        if(persona!=null){
        persona.setIdTipoDocumento(documentoSeleccionado);
        persona.setNumeroDocumento(txtDocumento.getText().toString());
        persona.setNombre(txtNombre.getText().toString());
        persona.setApellido(txtApellido.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPersonaActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.confirm_message_guardar_informacion);
        builder.setPositiveButton(R.string.confirm_action, (dialog, which) ->  actualizarInformacion());
        builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
        AlertDialog dialog = builder.create();
        dialog.show();}
        else{
            PersonaDTO newPersona=new PersonaDTO();
            newPersona.setIdTipoDocumento(documentoSeleccionado);
            newPersona.setNumeroDocumento(txtDocumento.getText().toString());
            newPersona.setNombre(txtNombre.getText().toString());
            newPersona.setApellido(txtApellido.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroPersonaActivity.this);
            builder.setCancelable(false);
            builder.setTitle(R.string.confirm);
            builder.setMessage(R.string.confirm_message_guardar_informacion);
            builder.setPositiveButton(R.string.confirm_action, (dialog, which) ->  insertarInformacion(newPersona) );
            builder.setNegativeButton(R.string.cancelar, (dialog, which) ->  dialog.cancel() );
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private void insertarInformacion(PersonaDTO newPersona){
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.insertar(newPersona);
        Intent intent=new Intent(RegistroPersonaActivity.this,MainActivity.class );
        this.startActivity(intent);

    }

    private void actualizarInformacion(){
        PersonaServiceImpl personaService = new PersonaServiceImpl(this);
        personaService.actualizar(persona,persona.getIdPersona());
        Intent intent=new Intent(RegistroPersonaActivity.this,MainActivity.class );
        this.startActivity(intent);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}