package idaritconverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IdaritConverter {

	public static void main(String[] args) throws IOException {
		BufferedReader br = null;
		List<String> Person = new ArrayList();
		List<String> Institution = new ArrayList();
		List<String> Vortrag = new ArrayList();
		List<String> Brief = new ArrayList();
		List<String> Grabung = new ArrayList();
		List<String> output = new ArrayList();
		try {
			// read file
			String line;
			br = new BufferedReader(new FileReader("C:\\temp\\Daten_Anne.txt"));
			while ((line = br.readLine()) != null) {
				String[] split = line.split("\t");
				if (split.length > 7) {
					if (split[7].equals("Person")) {
						Person.add(line);
					} else if (split[7].equals("Vortrag")) {
						Vortrag.add(line);
					}
				} 
				if (split.length > 6) {
					if (split[6].equals("Institution")) {
						Institution.add(line);
					}
				}
				if (split.length > 5) {
					if (split[5].equals("Brief")) {
						Brief.add(line);
					} else if (split[5].equals("Grabung")) {
						Grabung.add(line);
					}
				}
			}
			br.close();
			// create prefixes
			output.add("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .");
			output.add("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .");
			output.add("@prefix idarit: <http://github.com/i3mainz/idarit-2016#> .");
			output.add("@prefix prov: <http://www.w3.org/ns/prov#> .");
			output.add("@prefix vcard: <http://www.w3.org/2006/vcard/ns#> .");
			output.add("@prefix geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> .");
			output.add("@prefix dcterms: <http://purl.org/dc/terms/> .");
			output.add("@prefix owl: <http://www.w3.org/2002/07/owl#> .");
			output.add("@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .");
			output.add("@prefix foaf: <http://xmlns.com/foaf/0.1/> .");
			// create Person
			for (String entry : Person) {
				String[] split = entry.split("\t");
				output.add("idarit:P" + split[0] + " a foaf:Person.");
				output.add("idarit:P" + split[0] + " foaf:Name \"" + split[1] + "\".");
				output.add("idarit:P" + split[0] + " vcard:hasRole \"" + split[2] + "\".");
				if (!split[3].equals("")) {
					output.add("idarit:P" + split[0] + " rdfs:seeAlso <http://dbpedia.org/resource/" + split[3] + ">.");
				}
				if (!split[4].equals("")) {
					output.add("idarit:P" + split[0] + " owl:sameAs <" + split[4] + ">.");
				}
				output.add("idarit:P" + split[0] + " idarit:memberOf idarit:I" + split[5] + ".");
				if (!split[6].equals("")) {
					output.add("idarit:P" + split[0] + " idarit:worksAt idarit:G" + split[6] + ".");
				}
			}
			// create Institution
			for (String entry : Institution) {
				String[] split = entry.split("\t");
				output.add("idarit:I" + split[0] + " a vcard:Organization.");
				output.add("idarit:I" + split[0] + " a geo:SpatialThing.");
				output.add("idarit:I" + split[0] + " vcard:hasOrganizationName \"" + split[1] + "\".");
				output.add("idarit:I" + split[0] + " geo:lat \"" + split[2] + "\".");
				output.add("idarit:I" + split[0] + " geo:lon \"" + split[3] + "\".");
				if (!split[4].equals("")) {
					output.add("idarit:I" + split[0] + " rdfs:seeAlso <http://dbpedia.org/resource/" + split[4] + ">.");
				}
				if (!split[5].equals("")) {
					output.add("idarit:I" + split[0] + " dcterms:spatial <http://geonames.org/" + split[5] + ">.");
				}
			}
			// create Vortrag
			for (String entry : Vortrag) {
				String[] split = entry.split("\t");
				output.add("idarit:V" + split[0] + " a idarit:Vortrag.");
				output.add("idarit:V" + split[0] + " dcterms:date \"" + split[1] + "T00:00:00Z\"^^xsd:dateTime.");
				if (!split[2].equals("")) {
					output.add("idarit:V" + split[0] + " dcterms:subject \"" + split[2] + "\".");
				}
				if (!split[3].equals("")) {
					output.add("idarit:V" + split[0] + " dcterms:spatial <http://geonames.org/" + split[3] + ">.");
				}
				if (!split[4].equals("")) {
					output.add("idarit:V" + split[0] + " idarit:isAbout idarit:G" + split[4] + ".");
				}
				if (!split[5].equals("")) {
					output.add("idarit:V" + split[0] + " idarit:presentedAt idarit:I" + split[5] + ".");
				}
				output.add("idarit:V" + split[0] + " idarit:presentedBy idarit:P" + split[6] + ".");
			}
			// create Brief
			for (String entry : Brief) {
				String[] split = entry.split("\t");
				output.add("idarit:B" + split[0] + " a idarit:Brief.");
				output.add("idarit:B" + split[0] + " dcterms:date \"" + split[1] + "T00:00:00Z\"^^xsd:dateTime.");
				output.add("idarit:B" + split[0] + " dcterms:subject \"" + split[2] + "\".");
				output.add("idarit:B" + split[0] + " idarit:writtenBy idarit:P" + split[3] + ".");
				output.add("idarit:B" + split[0] + " idarit:sendTo idarit:P" + split[4] + ".");
			}
			// create Grabung
			for (String entry : Grabung) {
				String[] split = entry.split("\t");
				output.add("idarit:G" + split[0] + " a idarit:Grabung.");
				output.add("idarit:G" + split[0] + " rdfs:label \"" + split[1] + "\".");
				output.add("idarit:G" + split[0] + " geo:lat \"" + split[2] + "\".");
				output.add("idarit:G" + split[0] + " geo:lon \"" + split[3] + "\".");
				if (!split[4].equals("")) {
					String[] splitGrabung = split[4].split(",");
					for (String grabung : splitGrabung) {
						output.add("idarit:G" + split[0] + " idarit:visitedBy idarit:P" + grabung + ".");
					}
				}
			}
			// write output file
			File file = new File("C:\\temp\\Daten_Anne.ttl");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String entry : output) {
				bw.write(entry);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

		}
	}

}
