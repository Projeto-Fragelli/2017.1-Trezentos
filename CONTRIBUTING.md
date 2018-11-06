## Política de _branches_


### *_Master_*
_Branch_ de produção, com a versão mais estável do projeto. Commits e pushs direto são bloqueados, apenas podendo interagir através de _pull requests_ vindos da _developer_ ou _hotfix branches_.

### *_Developer_*
_Branch de desenvolvimento_, onde o trabalho testado, proveniente de outras _branches_, é agrupado com o objetivo de criar uma versão de _release_ para ser submetida à master. Commits e pushs também são bloqueados, podendo, somente, ser modificada através de _pull requests_ provenientes de _feature branches_.

### *_Feature Branches_*
_Branches_ criadas a partir da _developer_, para desenvolvimento de funcionalidades. Uma _featura branch_ deve ser referente à uma issue cadastrada no repositório. Quando finalizada a funcionalidade, deve-se submeter um _pull request_ direcionado a _developer_. Se as modificações cumprirem os critérios de aceitação e o _pull request_ for aceito, a branch deve ser apagada.
   #### *Nomeclatura*
   _Feature Branches_ devem seguir o padrão `n_nome_da_issue`, com n sendo o número da _issue_ correspondente.

### *_Hotfix Branches_*
_Branches_ criadas a partir da _master_, para correções simples de bugs e pontuais no ambiente de produção. Ao final as mudanças devem ser aplicadas tanto a _master_ quanto a _developer_.
   #### *Nomeclatura*
   _Hotfix Branches_ devem seguir o padrão `hotfix_n_nome_da_issue`, com n sendo o número da _issue_ correspondente.

### Mantendo _branches_ atualizadas
Recomenda-se manter as _branches_ de desenvolvimento pessoal sempre atualizadas antes de submeter _pull requests_. Deve se garantir que sua _branch_ possui todas as alterações mais recentes de sua _branch_ de origem. Deve-se utilizar os seguintes comandos:
```
git checkout branch_de_trabalho

git pull --rebase branch_de_origem # devel para features, master para hotfixes

git push origin branch_de_trabalho
```

## Política de commits 
Os commits devem possuir mensagens sucintas e obejtivas, tendo como característica a atomicidade de seu conteúdo, como por exemplo, métodos, que irão acrescentar para a funcionalidade. As mensagens de commits devem ser escritas em inglês, no imperativo. Por exemplo: 


``` 
  git commit -m "Create registration authentication test"
```

Ao invés de:
``` 
  git commit -m "Creating registration authentication test"
```

### Commits em pares
Ao se desenvolver funcionalidades utilizando _pair programming_, os commits, devem ser atribuídos para todos os colaboradores. Por isso, deve-se usar a tag _Co-authored-by_ do GitHub, seguindo os seguintes passos: 
* Após os arquivos estarem sendo rastreados pelo Git, deve se executar o comando:
```
git commit
```
* Isso abrirá o editor de texto padrão configurando para o Git. Digite a mensagem de commit, e após **duas** linhas em branco, é adicionada a co-autoria. Exemplo: 
```
Add repo contributing conduct


Co-authored-by: Lucas Dutra <ldutra98@gmail.com>
Co-authored-by: Pedro Rodrigues <peddroprp@gmail.com>
```

Dessa forma será gerado um rastreio mais detalhado de participação.