package br.com.wm.escolawm.escolawm.codecs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import br.com.wm.escolawm.escolawm.models.Aluno;
import br.com.wm.escolawm.escolawm.models.Contato;
import br.com.wm.escolawm.escolawm.models.Curso;
import br.com.wm.escolawm.escolawm.models.Habilidade;
import br.com.wm.escolawm.escolawm.models.Nota;

public class AlunoCodec implements CollectibleCodec<Aluno> {
	
	private Codec<Document> codec;

	public AlunoCodec(Codec<Document> codec) {
	    this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Aluno aluno, EncoderContext encoder) {
		ObjectId id = aluno.getId();
		String nome = aluno.getNome();
		Date dataNascimento = aluno.getDataNascimento();
		Curso curso = aluno.getCurso();
		List<Habilidade> habilidades = aluno.getHabilidades();
        List<Nota> notas = aluno.getNotas();
        Contato contato = aluno.getContato();

		Document documento = new Document();
		documento.put("_id", id);
		documento.put("nome", nome);
		documento.put("data_nascimento", dataNascimento);
		
		documento.put("curso", new Document("nome", curso.getNome()));
		
		if (habilidades != null) {
            List<Document> habilidadesDocument = new ArrayList<Document>();
            for (Habilidade habilidade : habilidades) {
                habilidadesDocument.add(
                        new Document().append("nome", habilidade.getNome()).append("nivel", habilidade.getNivel()));
            }
            documento.put("habilidades", habilidadesDocument);
        }

        if (notas != null) {
            List<Document> notasDocument = new ArrayList<Document>();
            for (Nota nota : notas) {
                notasDocument.add(new Document().append("valor", nota.getValor()));
            }
            documento.put("notas", notasDocument);

        }

        List<Double> coordinates = new ArrayList<Double>();
        if (contato != null) {
        	if(contato.getCoordinates() != null) {
		        for (Double location : contato.getCoordinates()) {
		            coordinates.add(location);
		        }
        	}
	        
	        documento.put("contato", new Document()
	                .append("endereco", contato.getEndereco())
	                .append("coordinates", coordinates)
	                .append("type", contato.getType()));
        }

		codec.encode(writer, documento, encoder);
	}

	@Override
	public Class<Aluno> getEncoderClass() {
		return Aluno.class;
	}

	@Override
	public Aluno decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		Aluno aluno = new Aluno();

		aluno.setId(document.getObjectId("_id"));
		aluno.setNome(document.getString("nome"));
		aluno.setDataNascimento(document.getDate("data_nascimento"));
		
		Document curso = (Document) document.get("curso");
		if(curso != null){
			String nomeCurso = curso.getString("nome");
			aluno.setCurso(new Curso(nomeCurso));
		}
		
		List<Document> notasDocument = (List<Document>) document.get("notas");
        if (notasDocument != null) {
            List<Nota> notas = new ArrayList<Nota>();
            for (Document documentNota : notasDocument) {
                notas.add(new Nota(documentNota.getDouble("valor")));
            }
            aluno.setNotas(notas);
        }
        
        List<Document> habilidadesDocument = (List<Document>) document.get("habilidades");
        if (habilidadesDocument != null) {
            List<Habilidade> habilidades = new ArrayList<Habilidade>();
            for (Document documentHabilidade : habilidadesDocument) {
                habilidades.add(
                        new Habilidade(documentHabilidade.getString("nome"), documentHabilidade.getString("nivel")));
            }
            aluno.setHabilidades(habilidades);
        }

        Document contato = (Document) document.get("contato");
        if (contato != null) {
            String endereco = contato.getString("endereco");
            List<Double> coordinates =  (List<Double>) contato.get ("coordinates");
            aluno.setContato(new Contato(endereco, coordinates));

        }

		return aluno;
	}

	@Override
	public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
		return documentHasId(aluno) ? aluno.criarId() : aluno;
	}

	@Override
	public boolean documentHasId(Aluno aluno) {
		return aluno.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(Aluno aluno) {
		if(!documentHasId(aluno)){
		    throw new IllegalStateException("Esse Document não tem id");
		}
		return new BsonString(aluno.getId().toHexString());
	}
	
	

}
