import os
import re

directory = 'src/main/java/com/clyvo/api/controller'

for filename in os.listdir(directory):
    if filename.endswith("Controller.java") and filename not in ["PetController.java", "TutorController.java", "MatchPreditivoController.java"]:
        filepath = os.path.join(directory, filename)
        with open(filepath, 'r') as f:
            content = f.read()

        # Add imports
        if 'import lombok.RequiredArgsConstructor;' not in content:
            content = content.replace('import org.springframework.web.bind.annotation.*;', 'import org.springframework.web.bind.annotation.*;\nimport lombok.RequiredArgsConstructor;\nimport org.springframework.web.servlet.support.ServletUriComponentsBuilder;\nimport java.net.URI;')
        
        # Replace @Autowired
        content = re.sub(r'@Autowired\s+private\s+(\w+)\s+(\w+);', r'private final \1 \2;', content)
        
        # Add @RequiredArgsConstructor
        if '@RequiredArgsConstructor' not in content:
            content = content.replace('@RestController', '@RestController\n@RequiredArgsConstructor')

        # Fix POST mapping to return Location header
        # Find @PostMapping ... salvar(@RequestBody TYPE var) { return ResponseEntity.ok(repository.save(var)); }
        # or similar.
        post_pattern = r'@PostMapping\s+public\s+ResponseEntity<(\w+)>\s+salvar\(\s*@RequestBody\s+\1\s+(\w+)\s*\)\s*\{\s*return\s+ResponseEntity\.(?:ok|status\(201\)\.body)\(([^)]+)\);\s*\}'
        
        def post_replacer(match):
            type_name = match.group(1)
            var_name = match.group(2)
            save_call = match.group(3)
            return f'''@PostMapping
    public ResponseEntity<{type_name}> salvar(@RequestBody {type_name} {var_name}) {{
        {type_name} salvo = {save_call};
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{{id}}")
                .buildAndExpand(salvo.getId())
                .toUri();
        return ResponseEntity.created(uri).body(salvo);
    }}'''
        
        content = re.sub(post_pattern, post_replacer, content)

        with open(filepath, 'w') as f:
            f.write(content)

