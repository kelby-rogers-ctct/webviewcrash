import * as THREE from "three";
import { GLTFLoader } from "./GLTFLoader.js";

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(
  75,
  window.innerWidth / window.innerHeight,
  0.1,
  10
);
camera.position.z = 5;

const light = new THREE.DirectionalLight();
scene.add(light);

const ambientLight = new THREE.AmbientLight();
scene.add(ambientLight);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

const gLTFLoader = new GLTFLoader();

const material = new THREE.ShaderMaterial({
  uniforms: {
    lightPosition: { type: "v3", value: new THREE.Vector3(5, 5, 5) },
  },
  vertexShader: `
  varying vec3 vNormal;
  varying vec3 vWorldPosition;
  void main() {
    vNormal = normalize(normalMatrix * normal);
    vWorldPosition = (modelMatrix * vec4(position, 1.0)).xyz;
    gl_Position = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
  }
`,
  fragmentShader: `
  uniform vec3 lightPosition;
  varying vec3 vNormal;
  varying vec3 vWorldPosition;
  void main() {
    vec3 lightDir = normalize(lightPosition - vWorldPosition);
    float brightness = max(dot(vNormal, lightDir), 0.0);
    vec3 reflectedColor = vec3(0.5, 0.5, 0.5);
    gl_FragColor = vec4(reflectedColor * brightness, 1.0);
  }
`,
});

gLTFLoader.loadAsync("./hex.glb").then((gltfScene) => {
  gltfScene.scene.traverse((child) => {
    if (child instanceof THREE.Mesh) {
      child.material = material;
    }
  });
  let hex = gltfScene.scene;
  scene.add(hex);

  const dialog = document.querySelector("#dialog");
  dialog.showModal();

  window.addEventListener("touchstart", closeDialogAndAnimate);
  window.addEventListener("click", closeDialogAndAnimate);

  function closeDialogAndAnimate() {
    dialog.close();
    animate();
  }

  // Animation loop
  function animate() {
    requestAnimationFrame(animate);

    if (hex) {
      hex.rotation.y += 0.01;
    }

    renderer.render(scene, camera);
  }
});

// Handle window resizing
window.addEventListener("resize", () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
});
