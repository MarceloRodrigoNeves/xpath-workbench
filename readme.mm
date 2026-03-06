<map version="1.0.10">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1771329307324" ID="ID_745833272" LINK="../../Pessoal/mapasMentais/Conhecimento.mm" MODIFIED="1771945092765" TEXT="XPathWorkbench">
<icon BUILTIN="licq"/>
<node CREATED="1771334924247" MODIFIED="1771334931222" POSITION="right" TEXT="Apelido">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1771334931915" MODIFIED="1771334942048" TEXT="m7-xpath">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1768142021485" MODIFIED="1768162459452" POSITION="right" TEXT="Vers&#xe3;o java utilizada no projeto">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1768157244050" MODIFIED="1768157486742" TEXT="sdk use java 21.0.1-tem">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1769900121558" FOLDED="true" ID="ID_491622923" MODIFIED="1772463121254" POSITION="right" TEXT="Comandos executado na constru&#xe7;&#xe3;o do projeto">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1769900185812" MODIFIED="1771329958831" TEXT="mvn archetype:generate -DgroupId=com.m7sistemas.xpathworkbench -DartifactId=xpath-workbench -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1771334521343" MODIFIED="1771334885582" POSITION="right" TEXT="Para n&#xe3;o dar erro no vscode, foi mudado o java padr&#xe3;o do sdkman:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1771334555002" MODIFIED="1771334890588" TEXT="sdk default java 21.0.1-tem">
<icon BUILTIN="tag_green"/>
</node>
<node CREATED="1771334581707" MODIFIED="1771334598050" TEXT="Caso tenha que voltar a vers&#xe3;o, executar o seguinte comando:">
<icon BUILTIN="messagebox_warning"/>
<node CREATED="1771334555002" MODIFIED="1771335194509" TEXT="sdk default java 8.0.382-amzn">
<icon BUILTIN="tag_green"/>
</node>
</node>
</node>
<node CREATED="1771333442723" MODIFIED="1771443922088" POSITION="right" TEXT="Executar para teste">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1771333450564" MODIFIED="1771333454471" TEXT="mvn clean javafx:run">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1771446381175" ID="ID_738582719" MODIFIED="1771446385810" POSITION="right" TEXT="Gerar executavel">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1771446386248" ID="ID_1551747411" MODIFIED="1771683142501" TEXT="mvn clean package -DskipTests">
<icon BUILTIN="tag_green"/>
</node>
<node CREATED="1771683157943" ID="ID_611675662" MODIFIED="1772816002798" TEXT="cp target/xpath-workbench-1.0-SNAPSHOT.jar ~/bin/xpath-workbench/xpath-workbench.jar">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1772815417198" ID="ID_920483008" MODIFIED="1772815502533" POSITION="right" TEXT="Criar atalho no Ubuntu 24.04.4 LTS">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815527348" ID="ID_1436101127" MODIFIED="1772815536851" TEXT="Acessar a pasta:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815537194" ID="ID_1312565707" MODIFIED="1772815539413" TEXT="~/.local/share/applications/">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1772815550147" ID="ID_18789375" MODIFIED="1772815566711" TEXT="Criar o arquivo de atalho:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815556988" ID="ID_823405047" MODIFIED="1772815559893" TEXT="xpath-workbench.desktop">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1772815589527" ID="ID_153455153" MODIFIED="1772815623143" TEXT="No arquivo &apos;xpath-workbench.desktop&apos; colocar o seguinte conteudo:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815609596" ID="ID_584331608" MODIFIED="1772815629648" TEXT="[Desktop Entry]&#xa;Version=1.0&#xa;Name=XPath Workbench&#xa;Comment=Editor avan&#xe7;ado de XPath&#xa;Exec=/home/marcelo/bin/xpath-workbench/run.sh&#xa;Icon=/home/marcelo/bin/xpath-workbench/icone.png&#xa;Terminal=false&#xa;Type=Application&#xa;Categories=Development;&#xa;StartupWMClass=com.m7sistemas.xpathworkbench.MainApp">
<icon BUILTIN="tag_green"/>
</node>
<node CREATED="1772815650588" ID="ID_396733941" MODIFIED="1772815693512" TEXT="Lembre de atualizar os diretos com base no seu computador">
<icon BUILTIN="messagebox_warning"/>
</node>
</node>
<node CREATED="1772815790913" ID="ID_605292944" MODIFIED="1772815801225" TEXT="Acessar a pasta:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815796328" ID="ID_93483881" MODIFIED="1772815800013" TEXT="~/bin/">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1772816030432" ID="ID_1339553439" MODIFIED="1772816045192" TEXT="Criar e acessar a pasta:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772816055496" ID="ID_1321478466" MODIFIED="1772816057832" TEXT="xpath-workbench">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1772815826812" ID="ID_379259128" MODIFIED="1772815850224" TEXT="Criar o arquivo:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815850696" ID="ID_1703547296" MODIFIED="1772815853996" TEXT="run.sh">
<icon BUILTIN="tag_green"/>
</node>
</node>
<node CREATED="1772815856060" ID="ID_885408517" MODIFIED="1772815874512" TEXT="No arquivo &apos;run.sh&apos; colocar o seguinte conteudo:">
<icon BUILTIN="Descriptor.grouping"/>
<node CREATED="1772815883173" ID="ID_283384943" MODIFIED="1772815886406" TEXT="#!/bin/bash&#xa;source &quot;$HOME/.sdkman/bin/sdkman-init.sh&quot;&#xa;sdk use java 21.0.1-tem&#xa;&#xa;cd /home/marcelo/bin/xpath-workbench&#xa;java -jar xpath-workbench.jar">
<icon BUILTIN="tag_green"/>
</node>
<node CREATED="1772815886830" ID="ID_146490743" MODIFIED="1772815949316" TEXT="Para funcionar, voc tem que e o SDKMAN e a versao referida do java, no caso java 21.0.1-tem">
<icon BUILTIN="messagebox_warning"/>
</node>
<node CREATED="1772815650588" ID="ID_1801307289" MODIFIED="1772815693512" TEXT="Lembre de atualizar os diretos com base no seu computador">
<icon BUILTIN="messagebox_warning"/>
</node>
</node>
<node CREATED="1772816018810" ID="ID_770849886" MODIFIED="1772816097218" TEXT="Se quiser que funcione o icone do projeto, tem que pegar la dentro do fonte do, e colocar na mesma pasta que o run.sh">
<icon BUILTIN="messagebox_warning"/>
</node>
</node>
</node>
</map>
